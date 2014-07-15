package unitario.model;

import models.analisadorLexico.Lexer;
import org.junit.Test;
import models.analisadorSintatico.ValidacaoAtribuicaoStrings;
import org.junit.Before;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TesteValidacaoAtribuicaoStrings {

    ValidacaoAtribuicaoStrings validador;
    ArrayList<String> entradaDoUsuario;
    private Lexer lexer;

    @Before
    public void setUp() throws Exception {
        entradaDoUsuario = new ArrayList<String>();
        lexer = new Lexer();
    }

    @Test
    public void verificaSePrimeiroTokenEUmIdentificadorDeVariavel() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaPrimeiroToken(), is("O primeiro token é uma variável."));
    }

    @Test
    public void seOPrimeiroTokenForUmaStringOValidaPrimeiroTokenRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario = lexer.tokenizar("\"variavel\" = \"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaPrimeiroToken(), is("Você digitou \"variavel\" e deveria ser uma variável."));
    }

    @Test
    public void seOPrimeiroTokenForUmNumeroOValidaPrimeiroTokenRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario = lexer.tokenizar("1 = \"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaPrimeiroToken(), is("Você digitou 1 e deveria ser uma variável."));
    }

    @Test
    public void seOSegundoTokenForUmaAtribuicaoEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaSegundoToken(), is("O segundo token é uma atribuição."));
    }

    @Test
    public void seOSegundoTokenNaoForUmaAtribuicaoEntaoRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x : \"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaSegundoToken(), is("Você digitou \":\" e deveria ser uma atribuição."));
    }

    @Test
    public void seOTerceiroTokenEUmaVariavelEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = banana");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTerceiroToken(), is("O terceiro token é válido."));
        assertThat(validador.validaTokensDepoisDoIgual(), is("O terceiro token é válido."));
    }

    @Test
    public void seOTerceiroTokenEUmaStringEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTerceiroToken(), is("O terceiro token é válido."));
        assertThat(validador.validaTokensDepoisDoIgual(), is("O terceiro token é válido."));
    }

    @Test
    public void seOTerceiroTokenNaoEumaStringOuUmaVariavelEntaoRetornaUmaMensagemDeErro() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = +");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTerceiroToken(), is("Você digitou \"+\" e deveria ser uma variável ou uma constante do tipo String."));
        assertThat(validador.validaTokensDepoisDoIgual(), is("Você digitou \"+\" e deveria ser uma variável ou uma constante do tipo String."));
    }

    @Test
    public void verificaQuantosTokensTemDepoisDoIgual() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"casa\" <> \"azul\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        int numeroDeTokens = validador.quantosTokensTemDepoisDoIgual();

        assertThat(numeroDeTokens, is(3));
    }

    @Test
    public void seHouverUmaConcatenacaoDeDuasConstantesStringsEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"casa\" <> \"azul\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("A concatenação foi feita corretamente."));
    }

    @Test
    public void seHouverUmaConcatenacaoDeDuasVariáveisStringsEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa <> azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("A concatenação foi feita corretamente."));
    }

    @Test
    public void seHouverUmaConcatenacaoComOSimboloDeIgualDeDuasVariáveisStringsEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa = azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("Você digitou \"=\" e deveria ser \"<>\"."));
    }

    @Test
    public void seHouverUmaConcatenacaoComUmaConstanteInteiraEUmaVariavelStringEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = 1 <> azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("Você digitou \"1\" e deveria ser uma variável ou constante String."));
    }

    @Test
    public void seHouverUmaConcatenacaoComOSimboloErradoComUmaConstanteInteiraEUmaVariavelStringEntaoOValidadorNaoIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = 1 : azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("Você digitou \"1\" e deveria ser uma variável ou constante String."));
    }

    @Test
    public void seHouverDuasConcatenacoesComTresVariaveisStringEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa <> grande <> azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("A concatenação foi feita corretamente."));
    }

    @Test
    public void seHouverDuasConcatenacoesComUmDosSimbolosErradosEntaoOValidadorIraReconhecer() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = casa <> grande : azul");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertThat(validador.validaTokensDepoisDoIgual(), is("Você digitou \":\" e deveria ser \"<>\"."));
    }

    @Test
    public void seHouverDoisErrosRetornaUmaListaComOsDoisErros() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x : 1 <> \"casa\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);
        ArrayList<String> mensagens = validador.getMensagens();

        assertThat(mensagens.size(), is(2));
        assertThat(mensagens.get(0), is("Você digitou \":\" e deveria ser uma atribuição."));
        assertThat(mensagens.get(1), is("Você digitou \"1\" e deveria ser uma variável ou constante String."));
    }

    @Test
    public void seTemErrosValidadorTerminaComEntradaInvalida() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x : 1 <> \"casa\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);
        boolean valida = validador.valida();

        assertThat(valida,is(false));
    }

    @Test
    public void seNaoTemErrosAValidacaoTerminaComSucesso() throws Exception {
        entradaDoUsuario = lexer.tokenizar("x = \"1\" <> \"casa\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);
        boolean valida = validador.valida();

        assertThat(valida,is(true));
    }
}