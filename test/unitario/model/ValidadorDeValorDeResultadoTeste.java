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
    private ArrayList<String> resultadosDoUsuario;
    ArrayList<String> resultadoDoProfessor;
    private boolean resultadosSaoIguais;

    @Before
    public void setUp() throws Exception {
        resultadoDoProfessor = new ArrayList<String>();


        when(exercicio.getResultadoDoProfessor()).thenReturn(resultadoDoProfessor);
        resultadosDoUsuario = new ArrayList<String>();
    }

    @Test
    public void resultadoDoUsuarioDeveTerOMesmoNumeroDeVarresQueODoResultadoDoProfessor() throws Exception {
        ValidadorDeValorDeResultado validadorDeValorDeResultado = new ValidadorDeValorDeResultado(exercicio);
        resultadoDoProfessor.add("Oi");
        resultadoDoProfessor.add("Tchau");

        resultadosDoUsuario.add("Oi");
        resultadosDoUsuario.add("Tchau");

        resultadosSaoIguais = validadorDeValorDeResultado.comparaResultados(resultadosDoUsuario);

        assertThat(resultadosSaoIguais, is(true));
    }

    //TODO: testes com valores Inteiros

    @Test
    public void resultadoComNumeroDeVariaveisEValoresIguaisAoEsperadoDeveRetornarTrue() throws Exception {
        ValidadorDeValorDeResultado validadorDeValorDeResultado = new ValidadorDeValorDeResultado(exercicio);

        resultadoDoProfessor.add("5");
        resultadoDoProfessor.add("8");

        resultadosDoUsuario.add("8");
        resultadosDoUsuario.add("5");

        resultadosSaoIguais = validadorDeValorDeResultado.comparaResultados(resultadosDoUsuario);

        assertThat(resultadosSaoIguais, is(true));
    }

    @Test
    public void resultadoComNumeroDeVariaveisDiferentesEValoresDiferentesDeveRetornarFalse() throws Exception {
        ValidadorDeValorDeResultado validadorDeValorDeResultado = new ValidadorDeValorDeResultado(exercicio);

        resultadoDoProfessor.add("5");
        resultadoDoProfessor.add("8");
        resultadoDoProfessor.add("9");

        resultadosDoUsuario.add("8");
        resultadosDoUsuario.add("5");

        resultadosSaoIguais = validadorDeValorDeResultado.comparaResultados(resultadosDoUsuario);

        assertThat(resultadosSaoIguais, is(false));
    }


}
