package models.parser;

import java.util.ArrayList;

public class Lexer {
    public ArrayList<String> tokenizar (String frase) {
        ArrayList<String> tokens = new ArrayList<String>();

        if (frase.equals("")) {
            return tokens;
        } else {
            String[] stringDividida = frase.split("[ ]+");
            for (int i = 0; i < stringDividida.length; i++) {
                if (stringDividida[i].length() > 1 && stringDividida[i].contains(":")) {
                    String[] tokenComDoisPontosNoMeio = stringDividida[i].split(":");
                    tokens.add(tokenComDoisPontosNoMeio[0]);
                    tokens.add(":");
                    tokens.add(tokenComDoisPontosNoMeio[1]);
                } else if (stringDividida[i].length() > 1 && stringDividida[i].startsWith("\"")
                        && !stringDividida[i].endsWith("\"")){
                    String temporaria = stringDividida[i];
                    i++;
                    while (!stringDividida[i].endsWith("\"")) {
                        temporaria += " " + stringDividida[i];
                        i++;
                    }
                    temporaria += " " + stringDividida[i];
                    tokens.add(temporaria);
                } else if (stringDividida[i].length() > 1 && stringDividida[i].contains("=")) {
                    String[] tokenComIgualdadeNoMeio = stringDividida[i].split("=");
                    tokens.add(tokenComIgualdadeNoMeio[0]);
                    tokens.add("=");
                    tokens.add(tokenComIgualdadeNoMeio[1]);
                } else if (stringDividida[i].length() > 2 && stringDividida[i].contains("<>")) {
                    String[] tokenComConcatenacaoNoMeio = stringDividida[i].split("<>");
                    tokens.add(tokenComConcatenacaoNoMeio[0]);
                    tokens.add("<>");
                    tokens.add(tokenComConcatenacaoNoMeio[1]);
                } else {
                    tokens.add(stringDividida[i]);
                }
            }
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).length() > 2 && tokens.get(i).contains("<>")) {
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
}