package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alunos4 on 21/07/14.
 */
public class ValidadorDeDeclaracaoDeVariavel implements Validador {

    TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeDeclaracaoDeVariavel(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean VerificaSeVariavelJaFoiDeclarada(String variavel){

        return tabelaDeSimbolos.simboloExiste(variavel);

    }

    @Override
    public boolean valida(ArrayList<String> tokens) {
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        return null;
    }
}
