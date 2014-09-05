package models.analisadorLexico;

import java.util.ArrayList;

public class Lexer {

    private ArrayList<String> tokens;
    private String frase;
    private ArrayList<String> listaDeSimbolos;

    public Lexer() {
        listaDeSimbolos = new ArrayList<String>();
        this.adicionaSimbolosNaLista();
    }

    public ArrayList<String> tokenizar (String frase) {
        tokens = new ArrayList<String>();
        this.frase = removeEspacosDesnecessariosDoFimEDoComeco(frase);
        if (fraseEstaVazia(this.frase)) {
            return tokens;
        } else {
            divideAFraseEmTokensEAdicionaNaLista(this.frase);
        }
        return tokens;
    }

    private String removeEspacosDesnecessariosDoFimEDoComeco(String frase) {
        frase = frase.trim();
        return frase;
    }

    private boolean fraseEstaVazia(String frase) {
        return frase.equals("");
    }

    //TODO - refatorar
    private void divideAFraseEmTokensEAdicionaNaLista(String frase) {
        boolean tokenFoiAdicionado = false;
        String palavra = "";
        for (int i = 0; i < frase.length(); i++) {
            if (frase.charAt(i) != ' ') {
                palavra += frase.charAt(i);

                if (i+1 < frase.length()) {
                    if (frase.charAt(i)=='\"' && (i + 1 < frase.length() - 1)) {
                        int t = i + 1;
                        while (frase.charAt(t) != '\"' && t < frase.length() - 1) {
                            palavra += frase.charAt(t);
                            t++;
                        }
                        palavra += frase.charAt(t);
                        i = t;
                        tokens.add(palavra);
                        palavra = "";
                    }
                }

                if (i+1 < frase.length()) {
                    if(frase.charAt(i)=='r' && frase.charAt(i+1)=='r' && frase.charAt(i+2)=='e' && frase.charAt(i+3)=='s') {
                        tokens.add("varres");
                        palavra = "";
                        i = i + 3;
                    } else if (listaDeSimbolos.contains(converteCaracterParaString(frase.charAt(i + 1))) && palavra!="") {
                        tokens.add(palavra);
                        palavra = "";
                    }
                    else if (frase.charAt(i+1) == ' '  && !palavra.equals("") || (frase.charAt(i+1) == '<'&&!palavra.equals(""))){
                        tokens.add(palavra);
                        palavra = "";
                    }
                } if (listaDeSimbolos.contains(palavra)) {
                    tokens.add(palavra);
                    palavra = "";
                }
                else if (i == frase.length()-1 && !palavra.equals("")) {
                    tokens.add(palavra);
                    palavra = "";
                }
            }
        }
    }

    private String converteCaracterParaString(char caracter) {
        return String.valueOf(caracter);
    }

    private void adicionaSimbolosNaLista(){
        listaDeSimbolos.add("varres");
        listaDeSimbolos.add("var");
        listaDeSimbolos.add("Inteiro");
        listaDeSimbolos.add("String");
        listaDeSimbolos.add("=");
        listaDeSimbolos.add(":");
        listaDeSimbolos.add("+");
        listaDeSimbolos.add("-");
        listaDeSimbolos.add("*");
        listaDeSimbolos.add("/");
        listaDeSimbolos.add("(");
        listaDeSimbolos.add(")");
        listaDeSimbolos.add("<>");
    }


    private boolean verificaTokenAnterior(int i){
        boolean verificador = false;

        if(tokens.get(i-1) == null){
            return verificador;
        }
            if (tokens.get(i-1).equals("=")
                    || tokens.get(i-1).equals("+")
                    || tokens.get(i-1).equals("-")
                    || tokens.get(i-1).equals("*")
                    || tokens.get(i-1).equals("/")
                    || tokens.get(i-1).equals("(")){
            verificador = true;
            }
        return verificador;
    }

    private boolean verificaSeProximoTokenEhNumero(int i){
        boolean verificador = false;

        if(tokens.get(i+1) == null){
            return verificador;
        }
            if (Character.isDigit(tokens.get(i + 1).charAt(0))){
            verificador = true;
        }
        else if(Character.isLetter(tokens.get(i + 1).charAt(0))){
            verificador = true;
        }
        return verificador;
    }
}
