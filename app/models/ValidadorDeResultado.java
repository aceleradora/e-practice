package models;

import models.analisadorLexico.Lexer;
import models.exercicioProposto.Exercicio;

import java.util.ArrayList;

public class ValidadorDeResultado {

    public boolean validaResultadoDoUsuario(Exercicio solucaoReal, SolucaoDoExercicio solucaoDoExercicio) {


        Lexer lexer = new Lexer();
        ArrayList<String> resultado = lexer.tokenizar(solucaoDoExercicio.getSolucaoDoUsuario());

        if(resultado.get(2).equals(solucaoReal.possivelSolucao.getSolucaoDoUsuario())){

            return true;
        }
        return false;
    }
}

