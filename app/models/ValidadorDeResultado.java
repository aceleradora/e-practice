package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.exercicioProposto.Exercicio;

import java.util.ArrayList;

public class ValidadorDeResultado {

    TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeResultado(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean validaResultadoDoUsuario(Exercicio solucaoReal, ArrayList<String> solucaoDoExercicio) {


        if(solucaoDoExercicio.get(2).equals(solucaoReal.possivelSolucao.getSolucaoDoUsuario())){
            return true;
        }
        return false;
    }

    public boolean comparaTiposDoResultado(SolucaoDoExercicio solucaoDoUsuario, SolucaoDoExercicio possivelSolucao) {

        String tipoSolucaoDoUsuario = tabelaDeSimbolos.getTipoSimbolo(solucaoDoUsuario.getSolucaoDoUsuario());
        String tipoPossivelSolucao = tabelaDeSimbolos.getTipoSimbolo(possivelSolucao.getSolucaoDoUsuario());

        if(tipoSolucaoDoUsuario.equals(tipoPossivelSolucao)) {
            return true;
        }
        return false;
    }
}

