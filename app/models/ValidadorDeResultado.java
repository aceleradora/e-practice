package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeResultado {
    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeResultado(TabelaDeSimbolos tabelaDeSimbolos) {
        identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean valida(ArrayList<String> solucao) {

        if (tabelaDeSimbolos.simboloExiste(solucao.get(1)) && tabelaDeSimbolos.verificaSeExisteVariavelDeResultado(solucao.get(1))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean tokenIdentificadoENumero(String token) {
        return identificadorDeToken.identifica(token).equals("NUMERO");
    }
}
