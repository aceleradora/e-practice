package unitario.model.analisadorLexico;

import models.SolucaoDoExercicio;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuebradorDeCodigoEmLinhasTeste {

    QuebradorDeCodigoEmLinhas quebrador;

    @Before
    public void setUp() throws Exception {
        quebrador = new QuebradorDeCodigoEmLinhas();
    }

    @Test
    public void quandoEntraUmCodigoDeUmaLinhaRetornaEleMesmo() throws Exception {
        String codigo = "var x : Integer";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);
        ArrayList<String> result = quebrador.quebra(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals(codigo, result.get(0));
    }

    @Test
    public void quandoEntraUmCodigoDeDuasLinhasRetornaUmArrayList() throws Exception {
        String codigo = "var x : Integer\n";
        codigo += "x = 1";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);

        ArrayList<String> result = quebrador.quebra(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals("var x : Integer", result.get(0));
        assertEquals("x = 1", result.get(1));
    }

    @Test
    public void quandoEntraUmaLinhaEmBrancoDeveRemoverDoArray() throws Exception {
        String codigo = "var x : Integera\n";
        codigo += "\n";
        codigo += "x = 1";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);

        ArrayList<String> result = quebrador.quebra(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals("x = 1", result.get(1));
    }

    @Test
    public void devePegarEnterDoWindows() throws Exception {
        String codigo = "var x : Integer\r";
        codigo += "x = 1";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);

        ArrayList<String> result = quebrador.quebra(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals("var x : Integer", result.get(0));
        assertEquals("x = 1", result.get(1));
    }


    @Test
    public void devePegarDoisEnters() throws Exception {
        String codigo = "var abacaxi : String\r\r";
        codigo += "x = \"blablabla\"\n\n";
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);

        ArrayList<String> result = quebrador.quebra(solucaoDoExercicio.solucaoDoUsuario);

        assertEquals("var abacaxi : String", result.get(0));
        assertEquals("x = \"blablabla\"", result.get(1));
    }
}
