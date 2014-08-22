package unitario.model.analisadorSintatico;

import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorGenerico;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ValidadorGenericoTeste {

    ValidadorGenerico validadorGenerico;
    Lexer lexer;

    @Before
    public void setUp() throws Exception {
        validadorGenerico = new ValidadorGenerico();
        lexer = new Lexer();
    }

    @Test
    public void dadoQueMinhaSentencaContemSomenteUmaStringEntaoRetornaMensagemDeErro() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("\"casa\"");
        validadorGenerico.valida(tokens);

        assertThat(validadorGenerico.retornaMensagemErro(), is("Código inválido.\nSintaxe não reconhecida."));
    }
}
