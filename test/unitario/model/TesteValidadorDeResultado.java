package unitario.model;

import models.SolucaoDoExercicio;
import models.TabelaDeSimbolos;
import models.ValidadorDeResultado;
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TesteValidadorDeResultado {

        SolucaoDoExercicio solucaoDoUsuario;
        SolucaoDoExercicio possivelSolucao;
        Exercicio exercicio;
        ValidadorDeResultado validadorDeResultado;
        @Mock TabelaDeSimbolos tabelaDeSimbolos;


    @Before
    public void setUp() throws Exception {

        solucaoDoUsuario = new SolucaoDoExercicio("resultado = 2");
        possivelSolucao = new SolucaoDoExercicio("2");
        exercicio = new Exercicio("1 + 1", possivelSolucao, false);
        validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos);


    }

    @Test
    public void quandoVarresIgualAoResultadoDoExercicioRetornaTrue() throws Exception {

        boolean resultado = validadorDeResultado.validaResultadoDoUsuario(exercicio, solucaoDoUsuario);

        assertThat(resultado, is(true));

    }

    @Test
    public void dadoQueResultadoDoUsuarioEPossivelSolucaoSaoDeTiposDiferentesRetornaFalse() throws Exception {

        solucaoDoUsuario = new SolucaoDoExercicio("varres x : String");
        when(tabelaDeSimbolos.getTipoSimbolo(solucaoDoUsuario.getSolucaoDoUsuario())).thenReturn("String");
        when(tabelaDeSimbolos.getTipoSimbolo(possivelSolucao.getSolucaoDoUsuario())).thenReturn("Inteiro");

        boolean resultado = validadorDeResultado.comparaTiposDoResultado(solucaoDoUsuario, possivelSolucao);

        assertThat(resultado, is(false));
    }
}
