package unitario.model.analisadorSintatico;

import models.TabelaDeSimbolos;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TesteValidadorDeDeclaracaoDeVariavel {

    public ArrayList<String> tokens = new ArrayList<String>();
    public ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    public TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
        tokens.add("var");
        tokens.add("x");
        tokens.add(":");
        tokens.add("String");
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
    }

    @Test
    public void retornaMensagemDeErroQuandoPrimeiroTokenNaoForVar() throws Exception {
        tokens.set(0, "Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A primeira palavra deveria ser \"var\". \n", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void retornaMensagemDeErroQuandoSegundoTokenNaoForIdentificadorDeVariavel() throws Exception {
        tokens.set(1, "1Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A segunda palavra deveria ser um identificador de variável válido. \n", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void retornaMensagemDeErroQuandoTerceiroTokenNaoForDoisPontos() throws Exception {
        tokens.set(2, "Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A terceira palavra deveria ser \":\". \n", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void retornaMensagemDeErroQuandoQuartoTokenNaoForTipoValidoDeVariavel() throws Exception {
        tokens.set(3, "Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A quarta palavra deveria ser um tipo válido de variável (string ou inteiro).", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void validaUmaLinhaDeDeclaracaoDeVariavelCorreta() throws Exception {
        boolean validacao = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals(true, validacao);
    }

    @Test
    public void adicionaSimboloXComoStringNaTabelaDeSimbolosSeADeclaracaoEstiverCorreta() throws Exception {
        validadorDeDeclaracaoDeVariavel.valida(tokens);
        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos();

        assertThat(tabelaDeSimbolos.getTipoSimbolo("x"), is("String"));
        assertThat(tokens.get(3), is("String"));
        assertThat(tabelaDeSimbolos.temSimbolo(tokens.get(1)), is(true));
    }

    @Test
    public void naoAdicionaSimboloNaTabelaDeSimbolosSeADeclaracaoEstiverErrada() throws Exception {
        tokens.set(0, "Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);
        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos();

        assertThat(tabelaDeSimbolos.simboloExiste("x"), is(false));
    }

    @Test
    public void retornaFalseQuandoValidaUmaDeclaracaoQueContemMaisDeQuatroTokens() throws Exception {
        tokens.add("String");
        validadorDeDeclaracaoDeVariavel.valida(tokens);
        boolean valido = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void retornaFalseQuandoValidaUmaDeclaracaoQueContemMenosDeQuatroTokens() throws Exception {
        tokens.remove(3);

        boolean valido = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void retornaFalseQuandoValidaDeclaracaoComNomeDoIdentificadorUsandoPalavraReservada() throws Exception {
        tokens.set(1, "var");

        boolean valido = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertThat(valido, is(false));
    }
}

