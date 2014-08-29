package unitario.model;

import models.ValidadorDeValorDeResultado;
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ValidadorDeValorDeResultadoTeste {
    @Mock Exercicio exercicio;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> resultadoDoProfessor = new ArrayList<String>();
        resultadoDoProfessor.add("Oi");
        resultadoDoProfessor.add("Tchau");

        when(exercicio.getResultadoDoProfessor()).thenReturn(resultadoDoProfessor);
    }

    @Test
    public void resultadoDoUsuarioDeveTerOMesmoNumeroDeVarresQueODoResultadoDoProfessor() throws Exception {
        ValidadorDeValorDeResultado validadorDeValorDeResultado = new ValidadorDeValorDeResultado(exercicio);
        ArrayList<String> resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("Oi");
        resultadosDoUsuario.add("Tchau");

        boolean resultadosSaoIguais = validadorDeValorDeResultado.comparaResultados(resultadosDoUsuario);

        assertThat(resultadosSaoIguais, is(true));
    }
}
