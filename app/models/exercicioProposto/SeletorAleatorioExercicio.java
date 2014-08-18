package models.exercicioProposto;

import java.util.List;
import java.util.Random;

public class SeletorAleatorioExercicio {

    private Exercicio exercicio;

    public SeletorAleatorioExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Exercicio buscaExercicio() {

        List<Exercicio> list = exercicio.todosNaoResolvidos();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }
}