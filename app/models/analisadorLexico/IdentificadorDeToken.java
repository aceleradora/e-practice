package models.analisadorLexico;

import java.util.HashMap;
import java.util.Map;

public class IdentificadorDeToken {

    private Map<String, String> dicionarioDeSimbolos;

    public IdentificadorDeToken() {
        dicionarioDeSimbolos = new HashMap<String, String>();
        adicionaSimbolosNoDicionario();
    }

    public String identifica(String token) {
        if (dicionarioDeSimbolos.containsKey(token)) {
            return dicionarioDeSimbolos.get(token);
        }
        if (token.charAt(0) == '\"' && token.charAt(token.length()-1) == '\"') {
            return "CONSTANTE_TIPO_STRING";
        }
        if (Character.isLetter(token.charAt(0))||token.charAt(0)=='_') {
            return "IDV";
        }
        if (Character.isDigit(token.charAt(0))) {
            if(verificaSeTodasOsCaracteresSaoNumeros(token)) {
                return "NUMERO";
            }
        }
        return "ERRO";
    }

    private void adicionaSimbolosNoDicionario(){
        dicionarioDeSimbolos.put("var", "PALAVRA_RESERVADA");
        dicionarioDeSimbolos.put("String", "TIPO_DE_VARIAVEL");
        dicionarioDeSimbolos.put("varres", "PALAVRA_RESERVADA");
        dicionarioDeSimbolos.put("Inteiro", "TIPO_DE_VARIAVEL");
        dicionarioDeSimbolos.put("=", "IGUAL");
        dicionarioDeSimbolos.put(":", "DECLARACAO");
        dicionarioDeSimbolos.put("+", "ADICAO");
        dicionarioDeSimbolos.put("-", "SUBTRACAO");
        dicionarioDeSimbolos.put("*", "MULTIPLICACAO");
        dicionarioDeSimbolos.put("/", "DIVISAO");
        dicionarioDeSimbolos.put("(", "PARENTESES_ABERTO");
        dicionarioDeSimbolos.put(")", "PARENTESES_FECHADO");
        dicionarioDeSimbolos.put("<>", "CONCATENACAO");
    }

    public boolean verificaSeTodasOsCaracteresSaoNumeros(String token){
        for (int i = 0; i < token.length(); i++) {
            if(!Character.isDigit(token.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
