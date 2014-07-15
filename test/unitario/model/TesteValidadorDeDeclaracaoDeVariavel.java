package unitario.model;

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
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tokens, tabelaDeSimbolos);
    }

    @Test
    public void retornaTrueSeOPrimeiroTokenForVar() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSePrimeiroTokenEVar());
    }

    @Test
    public void retornaTrueSeOSegundoTokenForIDV() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSeSegundoTokenEIdv());
    }

    @Test
    public void retornaTrueSeOTerceiroTokenForDoisPontos() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSeTerceiroTokenEDoisPontos());
    }

    @Test
    public void retornaTrueSeOQuartoTokenForTipoDeVariavel() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSeQuartoTokenETipoDeVariavel());
    }

    @Test
    public void retornaMensagemDeErroQuandoPrimeiroTokenNaoForVar() throws Exception {
        tokens.set(0, "Erro");

        assertEquals("a primeira palavra deveria ser \"var\" - ", validadorDeDeclaracaoDeVariavel.geraMensagensDeErro(tokens));
    }

    @Test
    public void retornaMensagemDeErroQuandoSegundoTokenNaoForIdentificadorDeVariavel() throws Exception {
        tokens.set(1, "1Erro");

        assertEquals("a segunda palavra deveria ser um identificador de variável válido - ", validadorDeDeclaracaoDeVariavel.geraMensagensDeErro(tokens));
    }

    @Test
    public void retornaMensagemDeErroQuandoTerceiroTokenNaoForDoisPontos() throws Exception {
        tokens.set(2, "Erro");

        assertEquals("a terceira palavra deveria ser \":\" - ", validadorDeDeclaracaoDeVariavel.geraMensagensDeErro(tokens));
    }

    @Test
    public void retornaMensagemDeErroQuandoQuartoTokenNaoForTipoValidoDeVariavel() throws Exception {
        tokens.set(3, "Erro");

        assertEquals("a quarta palavra deveria ser um tipo válido de variável (string ou inteiro)", validadorDeDeclaracaoDeVariavel.geraMensagensDeErro(tokens));
    }

    @Test
    public void validaUmaLinhaDeDeclaracaoDeVariavelCorreta() throws Exception {
        boolean validacao = validadorDeDeclaracaoDeVariavel.validaDeclaracao(tokens);

        assertEquals(true, validacao);
    }

    @Test
    public void adicionaSimboloXComoStringNaTabelaDeSimbolosSeADeclaracaoEstiverCorreta() throws Exception {
        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos(tokens);

        assertThat(tabelaDeSimbolos.getTipoSimbolo("x"), is("String"));
        assertThat(tokens.get(3), is("String"));
        assertThat(tabelaDeSimbolos.temSimbolo(tokens.get(1)), is(true));
    }

    @Test
    public void naoAdicionaSimboloNaTabelaDeSimbolosSeADeclaracaoEstiverErrada() throws Exception {
        tokens.set(0, "Erro");

        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos(tokens);

        assertThat(tabelaDeSimbolos.simboloExiste("x"), is(false));
    }

    @Test
    public void retornaFalseQuandoValidaUmaDeclaracaoQueContemMaisDeQuatroTokens() throws Exception {
        tokens.add("String");

        boolean valido = validadorDeDeclaracaoDeVariavel.validaDeclaracao(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void retornaFalseQuandoValidaUmaDeclaracaoQueContemMenosDeQuatroTokens() throws Exception {
        tokens.remove(3);

        boolean valido = validadorDeDeclaracaoDeVariavel.validaDeclaracao(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void retornaFalseQuandoValidaDeclaracaoComNomeDoIdentificadorUsandoPalavraReservada() throws Exception {
        tokens.set(1, "var");

        boolean valido = validadorDeDeclaracaoDeVariavel.validaDeclaracao(tokens);

        assertThat(valido, is(false));
    }
}

