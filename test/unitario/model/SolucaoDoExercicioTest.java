package unitario.model;

import models.SolucaoDoExercicio;
import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
