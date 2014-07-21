package unitario.model.analizadorSemantico;

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
import static org.mockito.Mockito.times;
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
    public void adicionaVariavelATabelaDeSimbolos() throws Exception {
        String declaracao = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracao);
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabelaMock);

        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokens);

        verify(tabelaMock).adicionaSimbolo("x", "Inteiro");
    }

    @Test
    public void adicionaDuasVariaveisDiferentesNaTabelaDeSimbolos() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        String declaracaoYpsilon = "var y : Inteiro";
        ArrayList<String> tokensDaDeclaracaoXis = lexer.tokenizar(declaracaoXis);
        ArrayList<String> tokensDaDeclaracaoYpsilon = lexer.tokenizar(declaracaoYpsilon);

        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokensDaDeclaracaoXis);
        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokensDaDeclaracaoYpsilon);

        assertThat(tabela.simboloExiste("x"), is(true));
        assertThat(tabela.simboloExiste("y"), is(true));
    }

    @Test
    public void verificaSeSimboloAdicionadoNaTabelaExiste() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracaoXis);

        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokens);
        boolean simboloExiste = validadorDeDeclaracao.verificaSeSimboloJaExisteNaTabela(tokens);

        assertThat(simboloExiste, is(true));
    }

    @Test
    public void retornaFalseAoVerificarUmSimboloInexistenteNaTabelaDeSimbolos() throws Exception {
        String declaracaoXis = "var x : Inteiro";
        ArrayList<String> tokens = lexer.tokenizar(declaracaoXis);

        boolean simboloExiste = validadorDeDeclaracao.verificaSeSimboloJaExisteNaTabela(tokens);

        assertThat(simboloExiste, is(false));
    }


}
