package unitario.model;

import models.SolucaoDoExercicio;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by alunos4 on 09/07/14.
 */
public class SolucaoDoExercicioTest {

    @Test
    public void SolucaoNaoPodeTerCorpoVazio() {
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio("solucaoDoUsuario");
        assertNotNull(solucaoDoExercicio.solucaoDoUsuario);
    }

}
