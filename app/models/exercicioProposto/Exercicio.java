package models.exercicioProposto;

import models.SolucaoDoExercicio;

public class Exercicio {

    int id;
    String enunciado;
    SolucaoDoExercicio possivelSolucao;
    boolean isResolvido = false;

    public Exercicio(int id, String enunciado, SolucaoDoExercicio possivelSolucao, boolean isResolvido) {
        this.id = id;
        this.enunciado = enunciado;
        this.possivelSolucao = possivelSolucao;
        this.isResolvido = isResolvido;
    }
}
