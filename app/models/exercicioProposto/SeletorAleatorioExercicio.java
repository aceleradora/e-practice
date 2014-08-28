package models.exercicioProposto;

import models.Usuario;

import java.util.List;
import java.util.Random;

public class SeletorAleatorioExercicio {
    private Usuario usuario;

    public SeletorAleatorioExercicio(Usuario usuario) {
        this.usuario = usuario;
    }

    public Exercicio buscaExercicioNaoResolvido() {
        List<Exercicio> list = usuario.todosNaoResolvidos();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }
}