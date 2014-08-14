package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeResultado {
    private IdentificadorDeToken identificadorDeToken;
    private Lexer lexer;
    private TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeResultado(TabelaDeSimbolos tabelaDeSimbolos) {
        identificadorDeToken = new IdentificadorDeToken();
        lexer = new Lexer();
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean valida(String solucao) {
        ArrayList<String> tokens = lexer.tokenizar(solucao);

        if (tokenIdentificadoENumero(tokens.get(2))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean tokenIdentificadoENumero(String token) {
        return identificadorDeToken.identifica(token).equals("NUMERO");
    }
}
