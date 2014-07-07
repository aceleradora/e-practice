package models.parser;

import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

/**
 * Created by alunos3 on 07/07/14.
 */
public class ValidadorDeDeclaracaoDeVariavel {

    IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

    public String validaDeclaracao(ArrayList<String> tokens) {
        if(identificadorDeToken.identifica(tokens.get(0)).equals("PALAVRA_RESERVADA")){
            return "var é o primeiro token";
        }
        return null;
    }


}
