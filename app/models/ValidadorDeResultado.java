package models;

import models.exercicioProposto.Exercicio;
import scala.tools.nsc.doc.model.Val;

import java.util.ArrayList;

public class ValidadorDeResultado implements Validador {

    TabelaDeSimbolos tabelaDeSimbolos;
    private ArrayList<String> solucaoDoExercicio;
    private Exercicio solucaoReal;

    public ValidadorDeResultado(TabelaDeSimbolos tabelaDeSimbolos, ArrayList<String> solucaoDoExercicio, Exercicio solucaoReal) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.solucaoDoExercicio = solucaoDoExercicio;
        this.solucaoReal = solucaoReal;
    }

    public boolean validaResultadoDoUsuario() {

        boolean comparaResultados = this.solucaoDoExercicio.get(2).equals(this.solucaoReal.possivelSolucao.getSolucaoDoUsuario());

        return comparaResultados;
    }

    public boolean comparaTiposDosResultados(SolucaoDoExercicio solucaoDoUsuario, SolucaoDoExercicio possivelSolucao) {

        String tipoSolucaoDoUsuario = tabelaDeSimbolos.getTipoSimbolo(solucaoDoUsuario.getSolucaoDoUsuario());
        String tipoPossivelSolucao = tabelaDeSimbolos.getTipoSimbolo(possivelSolucao.getSolucaoDoUsuario());

        return tipoSolucaoDoUsuario.equals(tipoPossivelSolucao);
    }

    @Override
    public boolean valida(ArrayList<String> tokens) {

        return true;
    }

    @Override
    public String retornaMensagemErro() {
        return null;
    }
}

