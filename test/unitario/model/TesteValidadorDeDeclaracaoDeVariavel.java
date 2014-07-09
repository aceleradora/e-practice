package unitario.model;

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

    @Before
    public void setUp() throws Exception {
        tokens.add("var");
        tokens.add("x");
        tokens.add(":");
        tokens.add("String");
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tokens);
    }

    @Test
    public void seOPrimeiroTokenEIgualAVar() throws Exception {
        String token = tokens.get(0);
        assertEquals("var é o Primeiro token", validadorDeDeclaracaoDeVariavel.validaPrimeiroToken());
    }

    @Test
    public void seOSegundoTokenEhUmIdv() throws Exception {
        String token = tokens.get(1);
        assertEquals("IDV é o Segundo token", validadorDeDeclaracaoDeVariavel.validaSegundoToken());
    }

    @Test
    public void seOTerceiroTokenEhUmDoisPontos() throws Exception {
        String token = tokens.get(2);
        assertEquals(": é o Terceiro token", validadorDeDeclaracaoDeVariavel.validaTerceiroToken());
    }

    @Test
    public void seOQuartoTokenEhUmTipo() throws Exception {
        String token = tokens.get(3);
        assertEquals("Tipo é o Quarto token", validadorDeDeclaracaoDeVariavel.validaQuartoToken());

    }


    @Test
    public void seEuErroOPrimeiroTokenEuconsigoCapturarEsteErro() throws Exception {
        String token = tokens.set(0, "erro");
        assertEquals("a primeira palavra deveria ser \"var\"", validadorDeDeclaracaoDeVariavel.validaPrimeiroToken());
    }

    @Test
    public void seEuErroOSegundoTokenEuconsigoCapturarEsteErro() throws Exception {
        String token = tokens.set(1, "1erro");
        assertEquals("a segunda palavra deveria ser um identificador de variável válido", validadorDeDeclaracaoDeVariavel.validaSegundoToken());
    }

    @Test
    public void seEuErroOTerceiroTokenEuconsigoCapturarEsteErro() throws Exception {
        String token = tokens.set(2, "erro");
        assertEquals("a terceira palavra deveria ser :", validadorDeDeclaracaoDeVariavel.validaTerceiroToken());
    }

    @Test
    public void seEuErroOQuartoTokenEuconsigoCapturarEsteErro() throws Exception {
        String token = tokens.set(3, "erro");
        assertEquals("a quarta palavra deveria ser um tipo válido de variável (string ou inteiro)", validadorDeDeclaracaoDeVariavel.validaQuartoToken());
    }

    @Test
    public void euConsigoValidarTodoUmaLinhaDeDeclaracao() throws Exception {
        String validacao = validadorDeDeclaracaoDeVariavel.valida();

        assertEquals(validacao,null);
    }

}

