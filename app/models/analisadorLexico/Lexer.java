package models.analisadorLexico;

import java.util.ArrayList;

public class Lexer {
    private ArrayList<String> tokens;
    private String frase;

    public Lexer() {
        tokens = new ArrayList<String>();
    }

    public ArrayList<String> tokenizar (String frase) {
        tokens = new ArrayList<String>();
        this.frase = removeEspacosDesnecessariosDoFimEDoComeco(frase);
        if (fraseEstaVazia(frase)) {
            return tokens;
        } else {
            String[] stringDividida = divideAFraseNosEspacosEmBranco(frase);
            for (String preToken : stringDividida) {
                tokens.add(preToken);
            }
            verificaCasosEspeciais(tokens);
            return tokens;
        }

    }

    private void verificaCasosEspeciais(ArrayList<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (tokenContemDoisPontosEMaisDeUmCaracter(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, ":", i);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemSinalDeIgualEMaisDeUmCaracter(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, "=", i);
                verificaCasosEspeciais(tokens);
            } else if (tokenComecaComAspasMasNaoTerminaComAspasETemMaisDeUmCaracter(token)) {
                String constanteASerAdicionada = token;
                int posicaoParaAdicionarAConstanteDepoisDePronta = i;
                tokens.remove(i);
                while (!tokens.get(i).endsWith("\"")) {
                    constanteASerAdicionada += " " + tokens.get(i);
                    tokens.remove(i);
                }
                constanteASerAdicionada += " " + tokens.get(i);
                tokens.remove(i);
                tokens.add(posicaoParaAdicionarAConstanteDepoisDePronta, constanteASerAdicionada);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemSimboloDeConcatenacaoETemMaisDeDoisCaracteres(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, "<>", i);
                verificaCasosEspeciais(tokens);
            }
        }
    }

    private void divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(String token, String simbolo, int indiceDaLista) {
        String[] stringDividida = token.split(simbolo);
        tokens.add(indiceDaLista, stringDividida[0]);
        tokens.add(indiceDaLista + 1, simbolo);
        tokens.add(indiceDaLista + 2, stringDividida[1]);
        tokens.remove(indiceDaLista + 3);
    }

    private boolean tokenContemSimboloDeConcatenacaoETemMaisDeDoisCaracteres(String token) {
        return token.contains("<>") && token.length() > 2;
    }

    private boolean tokenComecaComAspasMasNaoTerminaComAspasETemMaisDeUmCaracter(String token) {
        return (token.startsWith("\"") && !token.endsWith("\"")) && token.length() > 1;
    }

    private boolean tokenContemSinalDeIgualEMaisDeUmCaracter(String token) {
        return token.contains("=") && token.length() > 1;
    }

    private boolean tokenContemDoisPontosEMaisDeUmCaracter(String token) {
        return token.contains(":") && token.length() > 1;
    }

    private String[] divideAFraseNosEspacosEmBranco(String frase) {
        return frase.split("[ ]+");
    }

    private String removeEspacosDesnecessariosDoFimEDoComeco(String frase) {
        frase = frase.trim();
        return frase;
    }

    private boolean fraseEstaVazia(String frase) {
        return frase.equals("");
    }
}