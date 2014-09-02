package models;

import models.exercicioProposto.Exercicio;

import java.util.ArrayList;

public class ValidadorDeValorDeResultado {

    private Exercicio exercicio;

    public ValidadorDeValorDeResultado(Exercicio exericio){
        this.exercicio = exericio;
    }

    public boolean comparaResultados(ArrayList<String> resultadosDoUsuario) {
        if (resultadosDoUsuario.size() != exercicio.getResultadosDoProfessorComoLista().size()) {
            return false;
        }
        return resultadosDoUsuario.containsAll(exercicio.getResultadosDoProfessorComoLista());
    }
}