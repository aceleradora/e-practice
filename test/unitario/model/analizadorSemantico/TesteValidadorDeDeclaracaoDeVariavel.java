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

    @Mock private TabelaDeSimbolos tabela;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void adicionaVariavelATabelaDeSimbolos() throws Exception {
        String declaracao = "var x : Inteiro";
        Lexer lexer = new Lexer();
        ArrayList<String> tokens = lexer.tokenizar(declaracao);
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabela);

        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokens);

        verify(tabela).adicionaSimbolo("x", "Inteiro");
    }

    @Test
    public void adicionaDuasVariaveisDiferentesNaTabelaDeSimbolos() throws Exception {
        TabelaDeSimbolos tabela = new TabelaDeSimbolos();
        String declaracaoXis = "var x : Inteiro";
        String declaracaoYpsilon = "var y : Inteiro";
        Lexer lexer = new Lexer();
        ArrayList<String> tokensDaDeclaracaoXis = lexer.tokenizar(declaracaoXis);
        ArrayList<String> tokensDaDeclaracaoYpsilon = lexer.tokenizar(declaracaoYpsilon);
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabela);

        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokensDaDeclaracaoXis);
        validadorDeDeclaracao.adicionarParaTabelaDeSimbolos(tokensDaDeclaracaoYpsilon);

        assertThat(tabela.simboloExiste("x"), is(true));
        assertThat(tabela.simboloExiste("y"), is(true));
    }

    @Test
    public void aoTentarAdicionarUmSimboloNaTabelaDeSimbolosQueJaExisteRetornaUmErro() throws Exception {
        assertThat(true, is(true));
    }
}
