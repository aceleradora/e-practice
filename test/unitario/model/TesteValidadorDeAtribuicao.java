package unitario.model;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

public class TesteValidadorDeAtribuicao {
    Lexer lexer;
    IdentificadorDeToken identificadorDeToken;
    ValidadorDeAtribuicao validadorDeAtribuicao;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        identificadorDeToken = new IdentificadorDeToken();
        validadorDeAtribuicao = new ValidadorDeAtribuicao(lexer, identificadorDeToken);

    }

    @Test
    public void quandoPrimeiraPalavraEUmIDVRetornaIDV() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(0));

        assertThat(resultado, is("IDV"));
    }

    @Test
    public void quandoSegundaPalavraEUmOperadorIgualRetornaIgual() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(1));

        assertThat(resultado, is("IGUAL"));

    }

    @Test
    public void quandoTerceiraPalavraEUmNumeroRetornaNumero() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(2));

        assertThat(resultado, is("NUMERO"));

    }

    @Test
    public void retornaTrueQuandoOPrimeirTokenForUmIDV() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        boolean resultado = validadorDeAtribuicao.validaPrimeiroToken(tokens.get(0));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoOSegundoTokenForUmOperadorIgual() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        boolean resultado = validadorDeAtribuicao.validaSegundoToken(tokens.get(1));

        assertThat(resultado, is(true));
    }
}
