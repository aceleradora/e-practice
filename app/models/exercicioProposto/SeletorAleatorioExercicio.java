package models.exercicioProposto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeletorAleatorioExercicio {

    private Exercicio exercicio;

    public SeletorAleatorioExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Exercicio buscaExercicio() {
        List<Exercicio> list = exercicio.todosNaoResolvidos();

        return list.get(new Random().nextInt(list.size()));
    }
}