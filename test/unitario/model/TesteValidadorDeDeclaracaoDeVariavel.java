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
    public void testaSeEDeclaracaoDeVariavel() throws Exception {
        tokens.remove(3);
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tokens, tabelaDeSimbolos);

        boolean ehDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel.verificaSeEhVariavel();

        assertThat(ehDeclaracaoDeVariavel, is(false));
    }

    @Test
    public void seOPrimeiroTokenEIgualAVar() throws Exception {
        assertEquals("var é o Primeiro token", validadorDeDeclaracaoDeVariavel.validaPrimeiroToken());
    }

    @Test
    public void seOSegundoTokenEhUmIdv() throws Exception {
        assertEquals("IDV é o Segundo token", validadorDeDeclaracaoDeVariavel.validaSegundoToken());
    }

    @Test
    public void seOTerceiroTokenEhUmDoisPontos() throws Exception {
        assertEquals(": é o Terceiro token", validadorDeDeclaracaoDeVariavel.validaTerceiroToken());
    }

    @Test
    public void seOQuartoTokenEhUmTipo() throws Exception {
        assertEquals("Tipo é o Quarto token", validadorDeDeclaracaoDeVariavel.validaQuartoToken());
    }

    @Test
    public void seEuErroOPrimeiroTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(0, "Erro");
        assertEquals("a primeira palavra deveria ser \"var\" - ", validadorDeDeclaracaoDeVariavel.validaPrimeiroToken());
    }

    @Test
    public void seEuErroOSegundoTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(1, "1Erro");
        assertEquals("a segunda palavra deveria ser um identificador de variável válido - ", validadorDeDeclaracaoDeVariavel.validaSegundoToken());
    }

    @Test
    public void seEuErroOTerceiroTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(2, "Erro");
        assertEquals("a terceira palavra deveria ser : - ", validadorDeDeclaracaoDeVariavel.validaTerceiroToken());
    }

    @Test
    public void seEuErroOQuartoTokenEuconsigoCapturarEsteErro() throws Exception {
        tokens.set(3, "Erro");
        assertEquals("a quarta palavra deveria ser um tipo válido de variável (string ou inteiro)", validadorDeDeclaracaoDeVariavel.validaQuartoToken());
    }

    @Test
    public void euConsigoValidarTodoUmaLinhaDeDeclaracao() throws Exception {
        String validacao = validadorDeDeclaracaoDeVariavel.valida();
        assertEquals(validacao, "");
    }

    @Test
    public void seNaoHouverErrosAdicionaSimboloNaTabelaDeSimbolos() throws Exception {
        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos();
        assertThat(tabelaDeSimbolos.getTipoSimbolo("x"), is("String"));
    }

    @Test
    public void seHouverErrosNaoAdicionaSimboloNaTabelaDeSimbolos() throws Exception {
        tokens.set(0, "Erro");

        validadorDeDeclaracaoDeVariavel.adicionaVariavelNaTabelaDeSimbolos();
        assertThat(tabelaDeSimbolos.simboloExiste("x"), is(false));
    }
}

