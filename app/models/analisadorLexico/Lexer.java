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
//            frase = removeEspacosDesnecessariosDoFimEDoComeco(frase);
//            String[] stringDividida = divideAFraseNosEspacosEmBranco(frase);
//            for (int i = 0; i < stringDividida.length; i++) {
//                if (tokenTemMaisDeUmCaracterEContemDoisPontos(stringDividida[i])) {
//                    divideOTokenNosDoisPontosEAdicionaOsNovosTokensNaLista(stringDividida[i]);
//                } else if (tokenTemMaisDeUmCaracterEComecaETerminaComAspas(stringDividida[i])){
//                    String temporaria = stringDividida[i];
//                    i++;
//                    while (!stringDividida[i].endsWith("\"")) {
//                        temporaria += " " + stringDividida[i];
//                        i++;
//                    }
//                    temporaria += " " + stringDividida[i];
//                    adicionaOTokenNaLista(temporaria);
//                } else if (tokenTemMaisDeUmCaracterEContemSinalDeIgualdade(stringDividida[i])) {
//                    divideOTokenNoSinalDeIgualdadeEAdicionaOsNovosTokensNaLista(stringDividida[i]);
//                } else if (tokenContemMaisDeDoisCaracteresEContemSinalDeConcatenacao(stringDividida[i])) {
//                    divideOTokenNoSinalDeConcatenacaoEAdicionaOsNovosTokensNaLista(stringDividida[i]);
//                } else if (tokenTemMaisDeUmCaracterEIniciaComParentesesAberto(stringDividida[i])) {
//                    divideOTokenNoParentesesAbertoEAdicionaOsNovosTokensNaLista(stringDividida[i]);
//                } else if (tokenTemMaisDeUmCaracterETerminaComParentesesFechado(stringDividida[i])) {
//                    divideOTokenNoParentesesFechadoEAdicionaOsNovosTokensNaLista(stringDividida[i]);
//                } else {
//                    adicionaOTokenNaLista(stringDividida[i]);
//                }
//            }
//            for (int i = 0; i < tokens.size(); i++) {
//                if (tokenContemMaisDeDoisCaracteresEContemSinalDeConcatenacao(tokens.get(i))) {
//                    String[] tokenComConcatenacaoNoMeio = tokens.get(i).split("<>");
//                    tokens.add(i, tokenComConcatenacaoNoMeio[0]);
//                    tokens.add(i + 1, "<>");
//                    tokens.add(i + 2, tokenComConcatenacaoNoMeio[1]);
//                    tokens.remove(i + 3);
//                }
//            }
//            return tokens;
        }

    }

    private void verificaCasosEspeciais(ArrayList<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (tokenContemDoisPontosEMaisDeUmCaracter(token)) {
                divideOTokenNosDoisPontosEAdicionaOsNovosTokensNaLista(token, i);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemSinalDeIgualEMaisDeUmCaracter(token)) {
                divideOTokenNoSinalDeIgualEAdicionaOsNovosTokensNaLista(token, i);
                verificaCasosEspeciais(tokens);
            } else if ((token.startsWith("\"") && !token.endsWith("\"")) && token.length() > 1) {
                String constante = token;
                int posicaoInicial = i;
                tokens.remove(i);
                while (!tokens.get(i).endsWith("\"")) {
                    constante += " " + tokens.get(i);
                    tokens.remove(i);
                }
                constante += " " + tokens.get(i);
                tokens.remove(i);
                tokens.add(posicaoInicial, constante);
                verificaCasosEspeciais(tokens);
            }
        }
    }

    private void divideOTokenNoSinalDeIgualEAdicionaOsNovosTokensNaLista(String token, int indiceDaLista) {
        String[] stringDividida = token.split("=");
        tokens.add(indiceDaLista, stringDividida[0]);
        tokens.add(indiceDaLista + 1, "=");
        tokens.add(indiceDaLista + 2, stringDividida[1]);
        tokens.remove(indiceDaLista + 3);
    }

    private boolean tokenContemSinalDeIgualEMaisDeUmCaracter(String token) {
        return token.contains("=") && token.length() > 1;
    }

    private void divideOTokenNosDoisPontosEAdicionaOsNovosTokensNaLista(String token, int indiceDaLista) {
        String[] stringDividida = token.split(":");
        tokens.add(indiceDaLista, stringDividida[0]);
        tokens.add(indiceDaLista + 1, ":");
        tokens.add(indiceDaLista + 2, stringDividida[1]);
        tokens.remove(indiceDaLista + 3);
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