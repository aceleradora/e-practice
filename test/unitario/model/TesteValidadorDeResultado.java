package unitario.model;

import models.SolucaoDoExercicio;
import models.ValidadorDeResultado;
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TesteValidadorDeResultado {



        SolucaoDoExercicio solucaoDoUsuario;
        SolucaoDoExercicio possivelSolucao;
        Exercicio exercicio;
        ValidadorDeResultado validadorDeResultado;

    @Before
    public void setUp() throws Exception {

        solucaoDoUsuario = new SolucaoDoExercicio("resultado = 2");
        possivelSolucao = new SolucaoDoExercicio("2");
        exercicio = new Exercicio("1 + 1", possivelSolucao, false);
        validadorDeResultado = new ValidadorDeResultado();

    }

    @Test
    public void quandoVarresIgualAoResultadoDoExercicioRetornaTrue() throws Exception {

        boolean resultado = validadorDeResultado.validaResultadoDoUsuario(exercicio, solucaoDoUsuario);

        assertThat(resultado, is(true));

    }
}