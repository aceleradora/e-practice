package integracao;

import models.SolucaoDoExercicio;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class SolucaoDoExercicioTest {

    @Test
    public void quandoChamaOMetodoCreateCriaUmaSolucao() {
        running(fakeApplication(), new Runnable() {
            public void run() {

                SolucaoDoExercicio novaSolucao = new SolucaoDoExercicio("var x = 1");

                try {
                    SolucaoDoExercicio.create(novaSolucao);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                assertThat(novaSolucao.codigo).isNotNull();
            }
        });

    }

    @Test
    public void quandoChamaOMetodoAllRetornaOsObjetosDoBanco() throws Exception {
        running(fakeApplication(), new Runnable(){
            public void run(){

                List<SolucaoDoExercicio> lista = SolucaoDoExercicio.all();
                assertThat(lista).isNotEmpty();

            }
        });

    }
}
