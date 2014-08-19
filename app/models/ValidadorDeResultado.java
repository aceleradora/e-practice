package models;

import models.exercicioProposto.Exercicio;

import java.util.ArrayList;

public class ValidadorDeResultado implements Validador {

    TabelaDeSimbolos tabelaDeSimbolos;
    private ArrayList<String> tokensDoUsuario;
    private Exercicio solucaoReal;

    public ValidadorDeResultado(TabelaDeSimbolos tabelaDeSimbolos, ArrayList<String> solucaoDoExercicio, Exercicio solucaoReal) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.tokensDoUsuario = solucaoDoExercicio;
        this.solucaoReal = solucaoReal;
    }

    public boolean validaResultadoDoUsuario() {

        boolean comparaResultados = this.tokensDoUsuario.get(2).equals(this.solucaoReal.possivelSolucao.getSolucaoDoUsuario());

        return comparaResultados;
    }


    @Override
    public boolean valida(ArrayList<String> tokens) {
        tokensDoUsuario = tokens;
        if(validaResultadoDoUsuario()) {
            return true;
        }
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        String mensagemDeErro = "";

        if(!validaResultadoDoUsuario())
            mensagemDeErro = "O resultado do exercício não é o esperado!";
        else
            mensagemDeErro = "";
        return mensagemDeErro;
    }
}

