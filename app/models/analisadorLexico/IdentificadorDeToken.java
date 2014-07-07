package models.analisadorLexico;
public class IdentificadorDeToken {


    public String identifica(String token) {
        if (token.equals("var")) {
            return "PALAVRA_RESERVADA";
        } else if (token.equals("String")) {
            return "TIPO_DE_VARIAVEL";
        } else if (token.equals("Inteiro")) {
            return "TIPO_DE_VARIAVEL";
        } else if (token.equals("=")) {
            return "IGUAL";
     	} else if (token.equals("+")) {
            return "ADICAO";
        } else if (token.equals("-")) {
            return "SUBTRACAO";
        } else if (token.equals("*")) {
            return "MULTIPLICACAO";
        } else if (token.equals("/")) {
            return "DIVISAO";
        } else if (token.equals("(")) {
            return "PARENTESES_ABERTO";
        } else if (token.equals(")")) {
            return "PARENTESES_FECHADO";
        } else if (token.charAt(0) == '\"' && token.charAt(token.length()-1) == '\"') {
            return "CONSTANTE";
        } else if (Character.isLetter(token.charAt(0))) {
            return "IDV";
        } else if (Character.isDigit(token.charAt(0))) {
            if(!verificaSeTodasOsCaracteresSaoNumeros(token)) return "ERRO";
            else return "NUMERO";
        } else if (token.equals("<>")) {
            return "CONCATENACAO";

        } else return "INVALIDO";
    }

    public boolean verificaSeTodasOsCaracteresSaoNumeros(String token){
        for (int i = 1; i < token.length(); i++) {
            if(!Character.isDigit(token.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
