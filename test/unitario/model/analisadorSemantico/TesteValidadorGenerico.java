package unitario.model.analisadorSemantico;

import models.analisadorLexico.Lexer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TesteValidadorGenerico {

    ValidadorGenerico validadorGenerico;
    Lexer lexer;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        validadorGenerico = new ValidadorGenerico();
    }

    @Test
    public void dadoQueDigiteiAlgoEntaoAValidacaoSeraFalse() throws Exception {
        String declaracao = "dfbhahaehn ahaerh aherher";
        ArrayList<String> tokens = lexer.tokenizar(declaracao);

        assertThat(validadorGenerico.valida(tokens), is(false));
    }

    @Test
    public void dadoQueDigiteiAlgoEntaoAMensagemDeRetornoSeraDeErro() throws Exception {
        String declaracao = "dfbhahaehn ahaerh aherher";
        ArrayList<String> tokens = lexer.tokenizar(declaracao);
        validadorGenerico.valida(tokens);

        assertThat(validadorGenerico.retornaMensagemErro(), is("Código inválido. Sintaxe não reconhecida."));
    }
}
