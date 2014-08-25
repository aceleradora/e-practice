package integracao;

import models.SolucaoDoExercicio;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;


public class SolucaoDoExercicioTeste {

    @Test
    public void quandoChamaOMetodoCreateSalvaUmaSolucaoNoBanco() {
        running(fakeApplication(), new Runnable() {
            public void run() {
            SolucaoDoExercicio novaSolucao = new SolucaoDoExercicio("var x = 1");

            novaSolucao.save();
            SolucaoDoExercicio novaSolucaoNoBanco = SolucaoDoExercicio.find.ref(novaSolucao.id);

            assertEquals("var x = 1", novaSolucaoNoBanco.getSolucaoDoUsuario());
            SolucaoDoExercicio.delete(novaSolucao.id);
            }
        });
    }
}
