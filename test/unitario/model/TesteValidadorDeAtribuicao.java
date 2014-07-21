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
        validadorDeAtribuicao = new ValidadorDeAtribuicao(identificadorDeToken);
    }

    @Test
    public void retornaTrueQuandoOPrimeirTokenForUmIDV() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("morango = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaPrimeiroToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoOSegundoTokenForUmOperadorIgual() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaSegundoToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoTerceiroTokenForUmNumero() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaTerceiroToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoValidaALinha() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaqui = 42");
        boolean retorno = validadorDeAtribuicao.valida(tokens);

        assertThat(retorno, is(true));
    }

    @Test
    public void retornaMensagemdeErroQuandoEuErroAlgumaRegraSintatica() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("Abaxa = a");
        validadorDeAtribuicao.valida(tokens);
        String mensagem = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagem, is(not("")));
    }

    @Test
    public void retornaMensagemDeErroQuandoEuErroOIgual() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abaxa : 5");
        validadorDeAtribuicao.valida(tokens);
        String mensagem = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagem, is("\nEsperava \"=\" para atribuição.\n"));
    }
}


