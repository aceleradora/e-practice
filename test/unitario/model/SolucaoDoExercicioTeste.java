package unitario.model;

import models.SolucaoDoExercicio;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SolucaoDoExercicioTeste {

    @Test
    public void SolucaoNaoPodeTerCorpoVazio() {
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio("solucaoDoUsuario");
        assertNotNull(solucaoDoExercicio.solucaoDoUsuario);
    }
}
