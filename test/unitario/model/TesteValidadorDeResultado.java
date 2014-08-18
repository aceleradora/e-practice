 package unitario.model;

import models.SolucaoDoExercicio;
import models.TabelaDeSimbolos;
import models.ValidadorDeResultado;
import models.analisadorLexico.Lexer;
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TesteValidadorDeResultado {

        SolucaoDoExercicio solucaoDoUsuario;
        SolucaoDoExercicio possivelSolucao;
        Exercicio exercicio;
        ValidadorDeResultado validadorDeResultado;
        Lexer lexer;
        @Mock TabelaDeSimbolos tabelaDeSimbolos;
        ArrayList<String> tokens;


    @Before
    public void setUp() throws Exception {

        solucaoDoUsuario = new SolucaoDoExercicio("resultado = 2");
        possivelSolucao = new SolucaoDoExercicio("2");
        lexer = new Lexer();
        exercicio = new Exercicio("1 + 1", possivelSolucao, false);
        tokens = lexer.tokenizar(solucaoDoUsuario.getSolucaoDoUsuario());
        validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos, tokens, exercicio);

    }

    @Test
    public void quandoVarresIgualAoResultadoDoExercicioRetornaTrue() throws Exception {


        boolean resultado = validadorDeResultado.validaResultadoDoUsuario();

        assertThat(resultado, is(true));
    }

    @Test
    public void quandoVarresDoTipoStringEhIgualAOResultadoDoExercicioRetornaTrue() throws Exception {
        solucaoDoUsuario.solucaoDoUsuario = "resultado = \"abacaxi\"";
        exercicio.possivelSolucao = new SolucaoDoExercicio("\"abacaxi\"");
        tokens = lexer.tokenizar(solucaoDoUsuario.getSolucaoDoUsuario());

        ValidadorDeResultado validadorDeResultado1 = new ValidadorDeResultado(tabelaDeSimbolos, tokens, exercicio);

        boolean resultado = validadorDeResultado1.validaResultadoDoUsuario();

        assertThat(resultado, is(true));
    }

    @Test
    public void dadoQueResultadoDoUsuarioEPossivelSolucaoSaoDeTiposDiferentesRetornaFalse() throws Exception {

        solucaoDoUsuario = new SolucaoDoExercicio("varres x : String");
        when(tabelaDeSimbolos.getTipoSimbolo(solucaoDoUsuario.getSolucaoDoUsuario())).thenReturn("String");
        when(tabelaDeSimbolos.getTipoSimbolo(possivelSolucao.getSolucaoDoUsuario())).thenReturn("Inteiro");

        boolean resultado = validadorDeResultado.comparaTiposDosResultados(solucaoDoUsuario, possivelSolucao);

        assertThat(resultado, is(false));
    }

    @Test
    public void dadoQueOExercicioEstaInvalidoRetornaMensagemDeErro() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar(solucaoDoUsuario.getSolucaoDoUsuario());

        boolean resultado = validadorDeResultado.valida(tokens);

        assertThat(resultado, is(true));
    }

    
}
