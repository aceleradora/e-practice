package models.analisadorLexico;

import java.util.ArrayList;

public class Lexer {
    private ArrayList<String> tokens;

    public Lexer() {
        tokens = new ArrayList<String>();
    }

    public ArrayList<String> tokenizar (String frase) {
        tokens = new ArrayList<String>();
        if (fraseEstaVazia(frase)) {
            return tokens;
        } else {
            frase = removeEspacosDesnecessariosDoFimEDoComeco(frase);
            String[] stringDividida = divideAFraseNosEspacosEmBranco(frase);
            for (int i = 0; i < stringDividida.length; i++) {
                if (tokenTemMaisDeUmCaracterEContemDoisPontos(stringDividida[i])) {
                    divideOTokenNosDoisPontosEAdicionaOsNovosTokensNaLista(stringDividida[i]);
                } else if (tokenTemMaisDeUmCaracterEComecaETerminaComAspas(stringDividida[i])){
                    String temporaria = stringDividida[i];
                    i++;
                    while (!stringDividida[i].endsWith("\"")) {
                        temporaria += " " + stringDividida[i];
                        i++;
                    }
                    temporaria += " " + stringDividida[i];
                    adicionaOTokenNaLista(temporaria);
                } else if (tokenTemMaisDeUmCaracterEContemSinalDeIgualdade(stringDividida[i])) {
                    divideOTokenNoSinalDeIgualdadeEAdicionaOsNovosTokensNaLista(stringDividida[i]);
                } else if (tokenContemMaisDeDoisCaracteresEContemSinalDeConcatenacao(stringDividida[i])) {
                    divideOTokenNoSinalDeConcatenacaoEAdicionaOsNovosTokensNaLista(stringDividida[i]);
                } else if (tokenTemMaisDeUmCaracterEIniciaComParentesesAberto(stringDividida[i])) {
                    divideOTokenNoParentesesAbertoEAdicionaOsNovosTokensNaLista(stringDividida[i]);
                } else if (tokenTemMaisDeUmCaracterETerminaComParentesesFechado(stringDividida[i])) {
                    divideOTokenNoParentesesFechadoEAdicionaOsNovosTokensNaLista(stringDividida[i]);
                } else {
                    adicionaOTokenNaLista(stringDividida[i]);
                }
            }
            for (int i = 0; i < tokens.size(); i++) {
                if (tokenContemMaisDeDoisCaracteresEContemSinalDeConcatenacao(tokens.get(i))) {
                    String[] tokenComConcatenacaoNoMeio = tokens.get(i).split("<>");
                    tokens.add(i, tokenComConcatenacaoNoMeio[0]);
                    tokens.add(i + 1, "<>");
                    tokens.add(i + 2, tokenComConcatenacaoNoMeio[1]);
                    tokens.remove(i + 3);
                }
            }
            return tokens;
        }

    }

    private void adicionaOTokenNaLista(String token) {
        tokens.add(token);
    }

    private void divideOTokenNoParentesesFechadoEAdicionaOsNovosTokensNaLista(String token) {
        String[] tokenComParenteses = token.split("[)]");
        adicionaOTokenNaLista(tokenComParenteses[0]);
        adicionaOTokenNaLista(")");
    }

    private void divideOTokenNoParentesesAbertoEAdicionaOsNovosTokensNaLista(String token) {
        String[] tokenComParenteses = token.split("[(]");
        adicionaOTokenNaLista("(");
        adicionaOTokenNaLista(tokenComParenteses[1]);
    }

    private boolean tokenTemMaisDeUmCaracterETerminaComParentesesFechado(String token) {
        return token.length() > 1 && token.endsWith(")");
    }

    private boolean tokenTemMaisDeUmCaracterEIniciaComParentesesAberto(String token) {
        return token.length() > 1 && token.startsWith("(");
    }

    private void divideOTokenNoSinalDeConcatenacaoEAdicionaOsNovosTokensNaLista(String token) {
        String[] tokenComConcatenacaoNoMeio = token.split("<>");
        adicionaOTokenNaLista(tokenComConcatenacaoNoMeio[0]);
        adicionaOTokenNaLista("<>");
        adicionaOTokenNaLista(tokenComConcatenacaoNoMeio[1]);
    }

    private boolean tokenContemMaisDeDoisCaracteresEContemSinalDeConcatenacao(String token) {
        return token.length() > 2 && token.contains("<>");
    }

    private void divideOTokenNoSinalDeIgualdadeEAdicionaOsNovosTokensNaLista(String token) {
        String[] tokenComIgualdadeNoMeio = token.split("=");
        adicionaOTokenNaLista(tokenComIgualdadeNoMeio[0]);
        adicionaOTokenNaLista("=");
        adicionaOTokenNaLista(tokenComIgualdadeNoMeio[1]);
    }

    private boolean tokenTemMaisDeUmCaracterEContemSinalDeIgualdade(String token) {
        return token.length() > 1 && token.contains("=");
    }

    private boolean tokenTemMaisDeUmCaracterEComecaETerminaComAspas(String token) {
        return token.length() > 1 && token.startsWith("\"")
                && !token.endsWith("\"");
    }

    private void divideOTokenNosDoisPontosEAdicionaOsNovosTokensNaLista(String token) {
        String[] tokenComDoisPontosNoMeio = token.split(":");
        adicionaOTokenNaLista(tokenComDoisPontosNoMeio[0]);
        adicionaOTokenNaLista(":");
        adicionaOTokenNaLista(tokenComDoisPontosNoMeio[1]);
    }

    private boolean tokenTemMaisDeUmCaracterEContemDoisPontos(String token) {
        return token.length() > 1 && token.contains(":");
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