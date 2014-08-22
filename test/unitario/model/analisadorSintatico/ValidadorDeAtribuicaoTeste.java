package unitario.model.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.util.ArrayList;

public class ValidadorDeAtribuicaoTeste {
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
    public void retornaTrueQuandoOPrimeirTokenComecarComLetraMaiusculaEForUmIDV() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("Morango = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaPrimeiroToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaFalseQuandoOPrimeirTokenComecarComNumeroENaoForUmIDV() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("1morango = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaPrimeiroToken();

        assertThat(resultado, is(false));
    }

    @Test
    public void retornaTrueQuandoOSegundoTokenForUmOperadorIgual() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaSegundoToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaFalseQuandoOSegundoTokenForDoisPontos() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi : 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaSegundoToken();

        assertThat(resultado, is(false));
    }

    @Test
    public void retornaFalseQuandoOSegundoTokenForSimboloDeConcatenacao() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi <> 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaSegundoToken();

        assertThat(resultado, is(false));
    }

    @Test
    public void retornaFalseQuandoOSegundoTokenForUmIDV() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi abacaxi 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaSegundoToken();

        assertThat(resultado, is(false));
    }

    @Test
    public void retornaTrueQuandoTerceiroTokenForUmNumero() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaTerceiroToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoTerceiroTokenForUmIDV() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = casa");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaTerceiroToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoTerceiroTokenForUmaString() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = \"casa\"");
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.validaTerceiroToken();

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoALinhaForValida() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaqui = 42");
        boolean retorno = validadorDeAtribuicao.valida(tokens);

        assertThat(retorno, is(true));
    }

    @Test
    public void retornaFalseQuandoALinhaNaoForValida() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaqui = 1casa");
        boolean retorno = validadorDeAtribuicao.valida(tokens);

        assertThat(retorno, is(false));
    }

    @Test
    public void permiteUsarNomesComLetraMaiusculaNoInicio() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("Abaxa = a");
        validadorDeAtribuicao.valida(tokens);
        String mensagem = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagem, is(""));
    }


    @Test
    public void retornaMensagemDeErroQuandoEncontraNomeDeVariavelIncorreto() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("1abacaxi = \"frutas\"");

        validadorDeAtribuicao.valida(tokens);
        String mensagemDeErro = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagemDeErro, is("Nome de variável incorreto. \n"));
    }

    @Test
    public void retornaMensagemDeErroQuandoNaoEncontraSinalDeAtribuicao() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi : \"frutas\"");

        validadorDeAtribuicao.valida(tokens);
        String mensagemDeErro = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagemDeErro, is("Sinal de igual esperado para atribuição. \n"));
    }

    @Test
    public void retornaMensagemDeErroQuandoValorAtribuidoEInvalido() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = @");

        validadorDeAtribuicao.valida(tokens);
        String mensagemDeErro = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagemDeErro, is("Variável, valor numérico ou uma string são esperados. \n"));

    }

    @Test
    public void retornaTrueQuandoOTerceiroTokenForUmSinalDeAdicao() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = + 1");

        boolean retorno = validadorDeAtribuicao.valida(tokens);

        assertThat(retorno, is(true));

    }

    @Test
    public void retornaTrueQuandoTerceiroTokenForUmSinalDeSubtracao() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = +1");

        boolean retorno = validadorDeAtribuicao.valida(tokens);

        assertTrue(retorno);
    }

    @Test
    public void retornaMensagemDeErroQuandoTerceiroTokenNaoForUmSinalDeAdicaoOuSubtracao() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = * 2");

        validadorDeAtribuicao.valida(tokens);
        String mensagemDeErro = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagemDeErro, is("Existe(m) erros(s) sintático(s) na atribuição. \n"));
    }

    @Test
    public void retornaMensagemDeErroQuandoForAtribuidoDoisTokensEOTerceiroNaoForSinalPositivoOuNegativo() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = a b");

        validadorDeAtribuicao.valida(tokens);
        String mensagemDeErro = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagemDeErro, is("Existe(m) erros(s) sintático(s) na atribuição. \n"));

    }

    @Test
    public void retornaMensagemDeErroQuandoQuantidadeDeTokensForInvalida() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = a b c");

        validadorDeAtribuicao.valida(tokens);
        String mensagemDeErro = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(mensagemDeErro, is("Existe(m) erros(s) sintático(s) na atribuição. \n"));
    }


}


