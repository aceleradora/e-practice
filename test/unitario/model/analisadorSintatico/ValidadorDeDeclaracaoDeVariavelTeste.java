package unitario.model.analisadorSintatico;

import models.TabelaDeSimbolos;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ValidadorDeDeclaracaoDeVariavelTeste {

    public ArrayList<String> tokens = new ArrayList<String>();
    public models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    public TabelaDeSimbolos tabelaDeSimbolos;
    private boolean validacao;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
        tokens.add("var");
        tokens.add("x");
        tokens.add(":");
        tokens.add("String");
        validadorDeDeclaracaoDeVariavel = new models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
    }

    @Test
    public void retornaMensagemDeErroQuandoPrimeiroTokenNaoForVar() throws Exception {
        tokens.set(0, "PALAVRA_RESERVADA");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A primeira palavra deveria ser \"var\" ou \"varres\" para variável de resultado. \n", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void retornaMensagemDeErroQuandoSegundoTokenNaoForIdentificadorDeVariavel() throws Exception {
        tokens.set(1, "1Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A segunda palavra deveria ser um identificador de variável válido. \n", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void retornaMensagemDeErroQuandoTerceiroTokenNaoForDoisPontos() throws Exception {
        tokens.set(2, "Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A terceira palavra deveria ser \":\". \n", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void retornaMensagemDeErroQuandoQuartoTokenNaoForTipoValidoDeVariavel() throws Exception {
        tokens.set(3, "Erro");
        validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals("A quarta palavra deveria ser um tipo válido de variável (String ou Inteiro).", validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
    }

    @Test
    public void validaUmaLinhaDeDeclaracaoDeVariavelCorreta() throws Exception {
        boolean validacao = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertEquals(true, validacao);
    }

    @Test
    public void retornaFalseQuandoValidaUmaDeclaracaoQueContemMaisDeQuatroTokens() throws Exception {
        tokens.add("String");
        validadorDeDeclaracaoDeVariavel.valida(tokens);
        boolean valido = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void retornaFalseQuandoValidaUmaDeclaracaoQueContemMenosDeQuatroTokens() throws Exception {
        tokens.remove(3);

        boolean valido = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void retornaFalseQuandoValidaDeclaracaoComNomeDoIdentificadorUsandoPalavraReservada() throws Exception {
        tokens.set(1, "var");

        boolean valido = validadorDeDeclaracaoDeVariavel.valida(tokens);

        assertThat(valido, is(false));
    }

    @Test
    public void validaLinhaDeDeclaracaoDeVariavelCorretaComVarres() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("x");
        tokensVarres.add(":");
        tokensVarres.add("String");
        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertEquals(true, validacao);
    }

    @Test
    public void retornaFalseQuandoTemUmIDVInvalido() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("x!");
        tokensVarres.add(":");
        tokensVarres.add("String");
        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertThat(validacao,is(false));
    }

    @Test
    public void retornaFalseQuandoIDVContemArroba() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("abac@xi");
        tokensVarres.add(":");
        tokensVarres.add("Inteiro");

        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertThat(validacao, is(false));
    }

    @Test
    public void retornaFalseQuandoIDVContemHash() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("aba#42");
        tokensVarres.add(":");
        tokensVarres.add("Inteiro");

        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertThat(validacao, is(false));
    }

    @Test
    public void retornaFalseQuandoIDVContemCifrao() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("a$$ar");
        tokensVarres.add(":");
        tokensVarres.add("Inteiro");

        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertThat(validacao, is(false));
    }

    @Test
    public void retornaFalseQuandoIDVContemPorcentagem() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("a%bc");
        tokensVarres.add(":");
        tokensVarres.add("String");

        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertThat(validacao, is(false));
    }

    @Test
    public void retornaFalseQuandoIDVContem() throws Exception {
        ArrayList<String> tokensVarres = new ArrayList<String>();
        tokensVarres.add("varres");
        tokensVarres.add("a$$ar");
        tokensVarres.add(":");
        tokensVarres.add("Inteiro");

        validacao = validadorDeDeclaracaoDeVariavel.valida(tokensVarres);

        assertThat(validacao, is(false));
    }
}


