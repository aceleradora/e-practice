package unitario.model.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TesteValidadorDeDeclaracaoDeVariavel {

    @Mock private TabelaDeSimbolos tabelaMock;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    private TabelaDeSimbolos tabela;
    private Lexer lexer;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        tabela = new TabelaDeSimbolos();
        validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabela);
    }

    @Test
    public void dadoQueDeclaroUmaVariavelQueNaoExisteAdicionaVariavelATabelaDeSimbolos() throws Exception {
        String declaracao = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracao);
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabelaMock);

        validadorDeDeclaracao.valida(tokens);

        verify(tabelaMock).adicionaSimbolo("x", "Inteiro");
    }


    @Test
    public void dadoQueDeclareiDuasVariaveisDiferentesAdicionaAsDuasVariaveisDiferentesNaTabelaDeSimbolos() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        String declaracaoYpsilon = "var y : Inteiro";
        ArrayList<String> tokensDaDeclaracaoXis = lexer.tokenizar(declaracaoXis);
        ArrayList<String> tokensDaDeclaracaoYpsilon = lexer.tokenizar(declaracaoYpsilon);

        validadorDeDeclaracao.valida(tokensDaDeclaracaoXis);
        validadorDeDeclaracao.valida(tokensDaDeclaracaoYpsilon);

        assertThat(tabela.simboloExiste("x"), is(true));
        assertThat(tabela.simboloExiste("y"), is(true));
    }


    @Test
    public void quandoVerificaQueVariavelJaExisteNaTabelaDeSimbolosRetornaFalse() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracaoXis);

        tabela.adicionaSimbolo("x", "Inteiro");
        ValidadorDeDeclaracaoDeVariavel validador = new ValidadorDeDeclaracaoDeVariavel(tabela);

        assertThat(validador.valida(tokens), is(false));
    }

    @Test
    public void dadoQueTenhoUmaVariavelretornaTrueAoVerificarQueVariavelNaoExisteNaTabelaDeSimbolos() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracaoXis);

        assertThat(validadorDeDeclaracao.valida(tokens),is(true));
    }


    @Test
    public void dadoQueDeclareiUmaVariavelQueJaFoiDeclaradaRetornarMensagemDeErro() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracaoXis);

        tabela.adicionaSimbolo("x", "Inteiro");


        ValidadorDeDeclaracaoDeVariavel validador = new ValidadorDeDeclaracaoDeVariavel(tabela);
        validador.valida(tokens);

        assertThat(validador.retornaMensagemErro(), is("A variável x ja foi declarada."));
    }

    @Test
    public void dadoQueUmaVariavelFoiDeclaradaESalvaNaTabelaDeSimbolosRetornarTrue() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracaoXis);

        validadorDeDeclaracao.valida(tokens);
        assertThat(tabela.simboloExiste("x"),is(true));

    }
}
