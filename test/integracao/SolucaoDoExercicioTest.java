package integracao;

import models.SolucaoDoExercicio;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class SolucaoDoExercicioTest {

    @Test
    public void quandoChamaOMetodoCreateSalvaUmaSolucaoNoBanco() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            SolucaoDoExercicio novaSolucao = new SolucaoDoExercicio("var x = 1");
            novaSolucao.save();

            SolucaoDoExercicio novaSolucaoNoBanco = SolucaoDoExercicio.find.ref(novaSolucao.id);

            assertEquals(novaSolucaoNoBanco.solucaoDoUsuario, "var x = 1");
            SolucaoDoExercicio.delete(novaSolucao.id);
            }
        });
    }
}
