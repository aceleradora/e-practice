package unitario.parser;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

public class TesteValidadorDeAtribuicao {

    @Test
    public void quandoPrimeiraPalavraEUmIDVRetornaIDV() throws Exception {
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(0));

        assertThat(resultado, is("IDV"));
    }

    @Test
    public void quandoSegundaPalavraEUmOperadorIgualRetornaIgual() throws Exception {
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(1));

        assertThat(resultado, is("IGUAL"));

    }

    @Test
    public void quandoTerceiraPalavraEUmNumeroRetornaNumero() throws Exception {
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(2));

        assertThat(resultado, is("NUMERO"));

    }


}
