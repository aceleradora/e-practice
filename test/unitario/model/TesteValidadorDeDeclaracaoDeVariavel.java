package unitario.model;

import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by alunos3 on 07/07/14.
 */
public class TesteValidadorDeDeclaracaoDeVariavel {

    public ArrayList<String> tokens = new ArrayList<String>();
    public ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel();

    @Before
    public void setUp() throws Exception {
        tokens.add("var");
        tokens.add("x");
        tokens.add(":");
        tokens.add("String");
    }

    @Test
    public void SePrimeiroTokenEIgualAVar() throws Exception {
        assertEquals(validadorDeDeclaracaoDeVariavel.validaDeclaracao(tokens), "var é o primeiro token");
    }

    @Test
    public void seOSegundoTokenEhUmIdv() throws Exception {
        //assertEquals(validadorDeDeclaracaoDeVariavel.validaDeclaracao(tokens), "Idv é o segundo token");
    }

    @Test
    public void test() throws Exception {
        assertEquals(true, true);

    }
}
