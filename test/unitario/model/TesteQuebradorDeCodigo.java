package unitario.model;

import models.SolucaoDoExercicio;
import models.analisadorLexico.QuebradorDeCodigo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

public class TesteQuebradorDeCodigo {

    QuebradorDeCodigo quebrador;


    @Before
    public void setUp() throws Exception {
        quebrador = new QuebradorDeCodigo();
    }

    @Test
    public void quandoEntraUmCodigoDeUmaLinhaRetornaEleMesmo() throws Exception {
        String codigo = "var x : Integer";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);
        String result = quebrador.quebra(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals(codigo, result);

    }

    @Test
    public void quandoEntraUmCodigoDeDuasLinhasRetornaUmArrayList() throws Exception {
        String codigo = "var x : Integer\n";
        codigo += "x = 1";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);

        ArrayList<String> result = quebrador.quebraDuasLinhas(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals("var x : Integer", result.get(0));
        assertEquals("x = 1", result.get(1));
    }
}
