package integracao.controller;

import org.junit.Test;
import play.mvc.Result;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.*;

public class ExercicioTeste {
    @Test
    public void quandoChamaOMetodoCriaExerciciosRedirecionaParaOutraRota() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result;

                result = callAction(controllers.routes.ref.Exercicio.criaExercicios());

                assertThat(status(result)).isEqualTo(SEE_OTHER);
            }
        });
    }
}