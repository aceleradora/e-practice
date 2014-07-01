package models.parser;

import java.util.ArrayList;

public class Lexer {

    public ArrayList<String> tokenizar (String frase) {

        ArrayList<String> tokens = new ArrayList<String>();

        if (frase.equals("")) {
            return tokens;
        } else {
            String[] stringDividida = frase.split("[ ]+");
            for (String token : stringDividida) {
                if (token.length() > 1 && token.contains(":")) {
                    String[] tokenComIgualdadeNoMeio = token.split(":");
                    tokens.add(tokenComIgualdadeNoMeio[0]);
                    tokens.add(":");
                    tokens.add(tokenComIgualdadeNoMeio[1]);
                } else {
                    tokens.add(token);
                }
            }
            return tokens;
        }
    }
}
