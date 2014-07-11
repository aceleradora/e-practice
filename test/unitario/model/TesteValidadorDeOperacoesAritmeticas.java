package unitario.model;


import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import org.junit.*;
import scala.util.parsing.json.Lexer;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by aluno6 on 07/07/14.
 */
public class TesteValidadorDeOperacoesAritmeticas {

    private Lexer lexer;
    private ArrayList<String> tokens;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        tokens = new ArrayList<String>();
    }

    @Test
    public void validadorDeOPeracaoAritmeticaEntreSemInsercaoDeParenteses() throws Exception {
        ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas();
        tokens.add("2");
        tokens.add("+");
        tokens.add("1");
        tokens.add("-");
        tokens.add("2");

        assertThat(validadorDeOperacoesAritmeticas.validarArray(tokens), is("VALIDO"));
    }


}
