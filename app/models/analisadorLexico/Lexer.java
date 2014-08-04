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
        if (fraseEstaVazia(this.frase)) {
            return tokens;
        } else {
            String[] stringDividida = divideAFraseNosEspacosEmBranco(this.frase);
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
            if (tokenContemDoisPontosEMaisDeUmCaracterENaoContemAspas(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, ":", i);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemSinalDeIgualEMaisDeUmCaracter(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, "=", i);
                verificaCasosEspeciais(tokens);
            } else if (tokenComecaComAspasMasNaoTerminaComAspasETemMaisDeUmCaracter(token)) {
                divideTokenNoFimDaString(i, token);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemSimboloDeConcatenacaoETemMaisDeDoisCaracteres(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, "<>", i);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemOperadorMatemáticoETemMaisDeUmCaracter(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, capturaOperador(token), i);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemParentesesAbertoETemMaisDeUmCaracter(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, "(", i);
                verificaCasosEspeciais(tokens);
            } else if (tokenContemParentesesFechadoETemMaisDeUmCaracter(token)) {
                divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(token, ")", i);
                verificaCasosEspeciais(tokens);
            }
        }
    }

    private String capturaOperador(String token) {
        if(token.contains("+")){
            return "+";
        }else if(token.contains("-")) {
            return "-";
        }else if (token.contains("*")){
            return "*";
        }else if (token.contains("/")){
            return "/";
        }
        return "";
    }

    private boolean tokenContemOperadorMatemáticoETemMaisDeUmCaracter(String token) {
        return (token.contains("+")||token.contains("-")||token.contains("*")||token.contains("/")) && token.length() > 1;
    }

    private boolean tokenContemParentesesFechadoETemMaisDeUmCaracter(String token) {
        return token.contains(")") && token.length() > 1;
    }

    private boolean tokenContemParentesesAbertoETemMaisDeUmCaracter(String token) {
        return token.contains("(") && token.length() > 1;
    }

    private void divideTokenNoFimDaString(int i, String token) {
        if(fraseTemNumeroParDeAspas()) {
            String constanteASerAdicionada = token;
            int posicaoParaAdicionarAConstanteDepoisDePronta = i;
            removeToken(i);
            while (tokenNaoTerminaComAspas(i)) {
                constanteASerAdicionada += adicionaTokenComEspaco(i);
                removeToken(i);
            }
            constanteASerAdicionada += adicionaTokenComEspaco(i);
            removeToken(i);
            adicionaConstanteNaPosicao(constanteASerAdicionada, posicaoParaAdicionarAConstanteDepoisDePronta);
        }
    }

    private boolean fraseTemNumeroParDeAspas() {
        boolean isPar = false;
        int contaAspas = 0;
        for(String token:tokens){
            if(token.equals("\"")){
                contaAspas++;
            }
            if(contaAspas%2 == 0){
                isPar = true;
            }
        }
        return isPar;
    }

    private void adicionaConstanteNaPosicao(String constanteASerAdicionada, int posicaoParaAdicionarAConstanteDepoisDePronta) {
        tokens.add(posicaoParaAdicionarAConstanteDepoisDePronta, constanteASerAdicionada);
    }

    private String adicionaTokenComEspaco(int i) {
        return " " + tokens.get(i);
    }

    private void removeToken(int i) {
        tokens.remove(i);
    }

    private boolean tokenNaoTerminaComAspas(int i) {
        return !tokens.get(i).endsWith("\"");
    }

    private void divideOTokenNoSimboloEAdicionaOsNovosTokensNaLista(String token, String simbolo, int indiceDaLista) {
        String regexSimbolo = simbolo;
        if (simbolo.equals("(")) {
            regexSimbolo = "[(]";
        } else if (simbolo.equals(")")) {
            regexSimbolo = "[)]";
        } else if (simbolo.equals("+")) {
            regexSimbolo = "[+]";
        } else if(simbolo.equals("-")) {
            regexSimbolo = "[-]";
        } else if(simbolo.equals("*")) {
            regexSimbolo = "[*]";
        } else if(simbolo.equals("/")){
            regexSimbolo = "[/]";
        }
        if (token.startsWith(simbolo)) {
            String[] stringDividida = token.split(regexSimbolo);
            tokens.add(indiceDaLista, simbolo);
            tokens.add(indiceDaLista + 1, stringDividida[1]);
            tokens.remove(indiceDaLista + 2);
        } else if (token.endsWith(simbolo)) {
            String[] stringDividida = token.split(regexSimbolo);
            tokens.add(indiceDaLista, stringDividida[0]);
            tokens.add(indiceDaLista + 1, simbolo);
            tokens.remove(indiceDaLista + 2);
        } else {
            String[] stringDividida = token.split(regexSimbolo);
            tokens.add(indiceDaLista, stringDividida[0]);
            tokens.add(indiceDaLista + 1, simbolo);
            tokens.add(indiceDaLista + 2, stringDividida[1]);
            tokens.remove(indiceDaLista + 3);
        }
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

    private boolean tokenContemDoisPontosEMaisDeUmCaracterENaoContemAspas(String token) {
        return token.contains(":") && !token.contains("\"") && token.length() > 1;
    }

    private String[] divideAFraseNosEspacosEmBranco(String frase) {
//        String[] fraseDividida = frase.split("[ ]+");
//        fraseDividida = removeStringsVazias(fraseDividida);
//        return fraseDividida;
        return frase.split("[ ]+");
    }

//    private String[] removeStringsVazias(String[] fraseDividida){
//        for(int i = 0; i<fraseDividida.length;i++){
//            if(fraseDividida[i].equals("")){
//                for(int j = i;j<fraseDividida.length-1;j++){
//                    fraseDividida[j] = fraseDividida[j+1];
//                }
//            }
//        }
//        return fraseDividida;
//    }

    private String removeEspacosDesnecessariosDoFimEDoComeco(String frase) {
        frase = frase.trim();
        return frase;
    }

    private boolean fraseEstaVazia(String frase) {
        return frase.equals("");
    }
}