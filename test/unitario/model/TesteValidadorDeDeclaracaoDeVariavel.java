package unitario.model;

import models.TabelaDeSimbolos;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by alunos3 on 07/07/14.
 */
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
    public void seOPrimeiroTokenEIgualAVar() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSePrimeiroTokenEVar());
    }

    @Test
    public void seOSegundoTokenEhUmIdv() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSeSegundoTokenEIdv());
    }

    @Test
    public void seOTerceiroTokenEhUmDoisPontos() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSeTerceiroTokenEDoisPontos());
    }

    @Test
    public void seOQuartoTokenEhUmTipo() throws Exception {
        assertEquals(true, validadorDeDeclaracaoDeVariavel.validaSeQuartoTokenETipoDeVariavel());
    }

    @Test
    public void seEuErroOPrimeiroTokenEuconsigoCapturarAMensagemDesteErro() throws Exception {
        tokens.set(0, "Erro");

        assertEquals("a primeira palavra deveria ser \"var\" - ", validadorDeDeclaracaoDeVariavel.capturaMensagensDeErro());
    }

    @Test
    public void seEuErroOSegundoTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(1, "1Erro");

        assertEquals("a segunda palavra deveria ser um identificador de variável válido - ", validadorDeDeclaracaoDeVariavel.capturaMensagensDeErro());
    }

    @Test
    public void seEuErroOTerceiroTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(2, "Erro");

        assertEquals("a terceira palavra deveria ser \":\" - ", validadorDeDeclaracaoDeVariavel.capturaMensagensDeErro());
    }

    @Test
    public void seEuErroOQuartoTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(3, "Erro");

        assertEquals("a quarta palavra deveria ser um tipo válido de variável (string ou inteiro)", validadorDeDeclaracaoDeVariavel.capturaMensagensDeErro());
    }

    @Test
    public void euConsigoValidarTodoUmaLinhaDeDeclaracao() throws Exception {
        boolean validacao = validadorDeDeclaracaoDeVariavel.valida();

        assertEquals(validacao, true);
    }

    @Test
    public void seNaoHouverErrosAdicionaSimboloNaTabelaDeSimbolos() throws Exception {
        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos();

        assertThat(tabelaDeSimbolos.getTipoSimbolo("x"), is("String"));
        assertThat(tokens.get(3), is("String"));
        assertThat(tabelaDeSimbolos.temSimbolo(tokens.get(1)), is(true));
    }

    @Test
    public void seHouverErrosNaoAdicionaSimboloNaTabelaDeSimbolos() throws Exception {
        tokens.set(0, "Erro");

        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos();

        assertThat(tabelaDeSimbolos.simboloExiste("x"), is(false));
    }

    @Test
    public void verificaUmaDeclaracaoQueTenhaCincoTokens() throws Exception {
        tokens.add("String");

        boolean valido = validadorDeDeclaracaoDeVariavel.valida();

        assertThat(valido, is(false));
    }

    @Test
    public void verificaUmaDeclaracaoQueTenhaTresTokens() throws Exception {
        tokens.remove(3);

        boolean valido = validadorDeDeclaracaoDeVariavel.valida();

        assertThat(valido, is(false));
    }

    @Test
    public void verificaSeDeclaracaoDeVariavelComNomeVarGeraUmErro() throws Exception {
        tokens.set(1, "var");

        boolean valido = validadorDeDeclaracaoDeVariavel.valida();

        assertThat(valido, is(false));
    }
}

