package unitario.model;

import org.junit.Test;
import models.analisadorSintatico.ValidacaoAtribuicaoStrings;
import org.junit.Before;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class TesteValidacaoAtribuicaoStrings {

    ValidacaoAtribuicaoStrings validador;
    ArrayList<String> entradaDoUsuario;

    @Before
    public void setUp() throws Exception {
        entradaDoUsuario = new ArrayList<String>();
    }

    @Test
    public void verificaSePrimeiroTokenEUmIdentificadorDeVariavel() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaPrimeiroToken(), "O primeiro token é uma variável.");
    }

    @Test
    public void seOPrimeiroTokenForUmaStringOValidaPrimeiroTokenRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario.add("\"variavel\"");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaPrimeiroToken(), "Você digitou \"variavel\" e deveria ser uma variável.");
    }

    @Test
    public void seOPrimeiroTokenForUmNumeroOValidaPrimeiroTokenRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario.add("1");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaPrimeiroToken(), "Você digitou 1 e deveria ser uma variável.");
    }

    @Test
    public void seOSegundoTokenForUmaAtribuicaoEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaSegundoToken(), "O segundo token é uma atribuição.");
    }

    @Test
    public void seOSegundoTokenNaoForUmaAtribuicaoEntaoRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add(":");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaSegundoToken(), "Você digitou \":\" e deveria ser uma atribuição.");
    }

    @Test
    public void seOTerceiroTokenEUmaVariavelEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("banana");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTerceiroTokens(), "O terceiro token é válido.");
        assertEquals(validador.validaTokensDepoisDoIgual(), "O terceiro token é válido.");
    }

    @Test
    public void seOTerceiroTokenEUmaStringEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTerceiroTokens(), "O terceiro token é válido.");
        assertEquals(validador.validaTokensDepoisDoIgual(), "O terceiro token é válido.");
    }

    @Test
    public void seOTerceiroTokenNaoEumaStringOuUmaVariavelEntaoRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("+");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTerceiroTokens(), "Você digitou \"+\" e deveria ser uma variável ou uma constante do tipo String.");
        assertEquals(validador.validaTokensDepoisDoIgual(), "Você digitou \"+\" e deveria ser uma variável ou uma constante do tipo String.");
    }

    @Test
    public void verificaQuantosTokensTemDepoisDoIgual() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"casa\"");
        entradaDoUsuario.add("<>");
        entradaDoUsuario.add("\"azul\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        int numeroDeTokens = validador.quantosTokensTemDepoisDoIgual();

        assertEquals(numeroDeTokens, 3);
    }

    @Test
    public void seHouverUmaConcatenacaoDeDuasConstantesStringsEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"casa\"");
        entradaDoUsuario.add("<>");
        entradaDoUsuario.add("\"azul\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTokensDepoisDoIgual(), "A concatenação foi feita corretamente.");
    }

    @Test
    public void seHouverUmaConcatenacaoDeDuasVariáveisStringsEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("casa");
        entradaDoUsuario.add("<>");
        entradaDoUsuario.add("azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTokensDepoisDoIgual(), "A concatenação foi feita corretamente.");
    }

    @Test
    public void seHouverUmaConcatenacaoComOSimboloDeIgualDeDuasVariáveisStringsEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("casa");
        entradaDoUsuario.add(":");
        entradaDoUsuario.add("azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTokensDepoisDoIgual(), "Você digitou \":\" e deveria ser \"<>\".");
    }

    @Test
    public void seHouverUmaConcatenacaoComUmaConstanteInteiraEUmaVariavelStringEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("1");
        entradaDoUsuario.add("<>");
        entradaDoUsuario.add("azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTokensDepoisDoIgual(), "Você digitou \"1\" e deveria ser uma variável ou constante String.");
    }
    @Test
    public void seHouverUmaConcatenacaoComOSimboloErradoComUmaConstanteInteiraEUmaVariavelStringEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("1");
        entradaDoUsuario.add(":");
        entradaDoUsuario.add("azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTokensDepoisDoIgual(), "Você digitou \"1\" e deveria ser uma variável ou constante String.");
    }

    @Test
    public void seHouverDuasConcatenacoesComTresVariaveisStringEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("casa");
        entradaDoUsuario.add("<>");
        entradaDoUsuario.add("grande");
        entradaDoUsuario.add("<>");
        entradaDoUsuario.add("azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaTokensDepoisDoIgual(), "A concatenação foi feita corretamente.");
    }
}
