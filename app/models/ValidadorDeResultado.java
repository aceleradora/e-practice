package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeResultado {
    private IdentificadorDeToken identificadorDeToken;
    private Lexer lexer;

    public ValidadorDeResultado() {
        identificadorDeToken = new IdentificadorDeToken();
        lexer = new Lexer();
    }

    public boolean valida(String solucao) {
        ArrayList<String> tokens = lexer.tokenizar(solucao);

        if (tokenIdentificadoENumero(tokens.get(0))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean tokenIdentificadoENumero(String token) {
        return identificadorDeToken.identifica(token).equals("NUMERO");
    }
}
