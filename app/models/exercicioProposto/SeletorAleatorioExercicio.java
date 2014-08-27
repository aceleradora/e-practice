package models.exercicioProposto;

import models.Usuario;

import java.util.List;
import java.util.Random;

public class SeletorAleatorioExercicio {
    private Exercicio exercicio;

    public SeletorAleatorioExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Exercicio buscaExercicioNaoResolvido(Usuario usuario) {

//        List<Exercicio> list = exercicio.todosNaoResolvidos();
        List<Exercicio> list = usuario.todosNaoResolvidos();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }
}