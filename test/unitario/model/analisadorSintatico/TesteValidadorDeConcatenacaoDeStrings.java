package unitario.model.analisadorSintatico;

import models.analisadorLexico.Lexer;
import org.junit.Ignore;
import org.junit.Test;
import models.analisadorSintatico.ValidadorDeConcatenacaoDeStrings;
import org.junit.Before;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TesteValidadorDeConcatenacaoDeStrings {

    ValidadorDeConcatenacaoDeStrings validador;
    ArrayList<String> entradaDoUsuario;
    private Lexer lexer;

    @Before
    public void setUp() throws Exception {
        entradaDoUsuario = new ArrayList<String>();
        validador = new ValidadorDeConcatenacaoDeStrings();
        lexer = new Lexer();
    }

    @Test
    public void seOTerceiroTokenEUmaVariavelEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = banana");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("1) O terceiro token é válido.\n"));
    }

    @Test
    public void seOTerceiroTokenEUmaStringEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"banana\"");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("1) O terceiro token é válido.\n"));
    }

    @Test
    public void seOTerceiroTokenNaoEumaStringOuUmaVariavelEntaoRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = +");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("2) Você digitou \"+\" e deveria ser uma variável ou uma constante do tipo String.\n"));
    }

    @Test
    public void seHouverUmaConcatenacaoDeDuasConstantesStringsEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"casa\" <> \"azul\"");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is(""));
    }

    @Test
    public void seHouverUmaConcatenacaoDeDuasVariáveisStringsEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa <> azul");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is(""));
    }

    @Test
    public void seHouverUmaConcatenacaoComOSimboloDeIgualDeDuasVariáveisStringsEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa = azul");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("1) Você digitou \"=\" e deveria ser \"<>\".\n"));
    }

    @Test
    public void seHouverUmaConcatenacaoComUmaConstanteInteiraEUmaVariavelStringEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = 1 <> azul");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("1) Você digitou \"1\" e deveria ser uma variável ou constante String.\n"));
    }

    @Test
    public void seHouverUmaConcatenacaoComOSimboloErradoComUmaConstanteInteiraEUmaVariavelStringEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = 1 : azul");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("1) Você digitou \"1\" e deveria ser uma variável ou constante String.\n"));
    }

    @Test
    public void seHouverDuasConcatenacoesComTresVariaveisStringEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa <> grande <> azul");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is(""));
    }

    @Test
    public void seHouverDuasConcatenacoesComUmDosSimbolosErradosEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa <> grande : azul");
        validador.valida(entradaDoUsuario);
        assertThat(validador.retornaMensagemErro(), is("1) Você digitou \":\" e deveria ser \"<>\".\n"));
    }

    @Test
    public void seHouverEntradaDeDadosVerificaTamanhoDaMensagemErro() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x : 1 <> \"casa\"");
        validador.valida(entradaDoUsuario);
        String mensagens = validador.retornaMensagemErro();
        assertThat(mensagens.length(), not(0));
    }

    @Test
    public void seTemErrosValidadorTerminaComEntradaInvalida() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x : 1 <> \"casa\"");
        boolean valida = validador.valida(entradaDoUsuario);
        assertThat(valida,is(false));
    }

    @Test
    public void seNaoTemErrosAValidacaoTerminaComSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"1\" <> \"casa\"");
        boolean valida = validador.valida(entradaDoUsuario);
        assertThat(valida,is(true));
    }

    @Test
    public void tokenizaUmaLinhaQueTenhaUmEspacoAntesDaExpressaoEVerificaSeAConcatenacaoEstaCorreta() throws Exception {
        entradaDoUsuario = lexer.tokenizar(" x = \"1\" <> \"casa\"");
        boolean valida = validador.valida(entradaDoUsuario);
        assertThat(valida,is(true));
    }
}