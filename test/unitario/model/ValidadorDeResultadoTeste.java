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

@RunWith(MockitoJUnitRunner.class)
public class ValidadorDeResultadoTeste {

        SolucaoDoExercicio solucaoDoUsuario;
        SolucaoDoExercicio possivelSolucao;
        SolucaoDoExercicio solucaoDoProfessor;

        Exercicio exercicio;
        Lexer lexer;
        @Mock TabelaDeSimbolos tabelaDeSimbolos;
        ArrayList<String> tokens;

    @Before
    public void setUp() throws Exception {

        solucaoDoUsuario = new SolucaoDoExercicio("resultado = 2");
        possivelSolucao = new SolucaoDoExercicio("2");
        solucaoDoProfessor = new SolucaoDoExercicio("resultado = 2");
        lexer = new Lexer();
        exercicio = new Exercicio("enunciado 1 + 1", possivelSolucao, solucaoDoProfessor, null);
        tokens = lexer.tokenizar(solucaoDoUsuario.getSolucaoDoUsuario());
    }

    @Test
    public void quandoVarresDoTipoInteiroEhIgualAoResultadoDoExercicioRetornaTrue() throws Exception {

        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos, tokens, exercicio);

        boolean resultado = validadorDeResultado.validaResultadoDoUsuario();

        assertThat(resultado, is(true));
    }

    @Test
    public void quandoVarresDoTipoStringEhIgualAOResultadoDoExercicioRetornaTrue() throws Exception {
        solucaoDoUsuario.solucaoDoUsuario = "resultado = \"abacaxi\"";
        exercicio.possivelSolucao = new SolucaoDoExercicio("\"abacaxi\"");
        tokens = lexer.tokenizar(solucaoDoUsuario.getSolucaoDoUsuario());

        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos, tokens, exercicio);

        boolean resultado = validadorDeResultado.validaResultadoDoUsuario();

        assertThat(resultado, is(true));
    }

    @Test
    public void dadoQueOExercicioEstaInvalidoRetornaFalse() throws Exception {

        String solucaoDoUsuario = "x = 3";
        tokens = lexer.tokenizar(solucaoDoUsuario);
        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos, tokens, exercicio);

        boolean resultado = validadorDeResultado.valida(tokens);

        assertThat(resultado, is(false));
    }

    @Test
    public void quandoRespostasNaoCombinamRetornaMensagemDeErro() throws Exception {

        String solucaoDoUsuario = "x = 3";
        tokens = lexer.tokenizar(solucaoDoUsuario);
        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos, tokens, exercicio);

        validadorDeResultado.valida(tokens);

        assertThat(validadorDeResultado.retornaMensagemErro(), is("O resultado do exercício não é o esperado!"));
    }
}
