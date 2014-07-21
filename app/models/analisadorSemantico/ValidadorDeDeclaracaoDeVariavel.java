package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;

import java.util.ArrayList;

/**
 * Created by alunos4 on 21/07/14.
 */
public class ValidadorDeDeclaracaoDeVariavel implements Validador {

    TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeDeclaracaoDeVariavel(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public void adicionarParaTabelaDeSimbolos(ArrayList<String> tokens) {
        tabelaDeSimbolos.adicionaSimbolo(tokens.get(1), tokens.get(3));
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
