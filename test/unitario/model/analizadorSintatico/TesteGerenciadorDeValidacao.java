package unitario.model.analizadorSintatico;

import models.analisadorSintatico.*;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TesteGerenciadorDeValidacao {

    @Mock Lexer lexer;
    @Mock IdentificadorDeToken identificadorDeToken;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    @Mock ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeString;

    private GerenciadorDeValidacao gerenciadorDeValidacao;
    private String sentencaDeclaracao;
    private ArrayList<String> listaDeTokensDeclaracao;
    private String sentencaAtribuicao;
    private ArrayList<String> listaDeTokensAtribuicao;
    private String sentencaOperacaoAritmetica;
    private ArrayList<String> listaDeTokensOperacaoAritmetica;
    private String sentencaConcatenacaoString;
    private ArrayList<String> listaDeTokensConcatenacaoString;

    @Before
    public void setUp() throws Exception {
        GerenciadorBuilder builder = new GerenciadorBuilder();

        gerenciadorDeValidacao = builder.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeAtribuicao)
                .com(validadorDeOperacoesAritmeticas)
                .com(validadorDeConcatenacaoDeString)
                .geraGerenciador();

        sentencaDeclaracao = "var x : String";
        sentencaAtribuicao = "x = 1";
        sentencaOperacaoAritmetica = "x = 1 + 1";
        sentencaConcatenacaoString = "x = \"abacaxi\" <> \"verde\"";

        criaListaDeTokensDeDeclaracao();
        criaListaDeTokensDeAtribuicao();
        criaListaDeTokensDeOperacaoAritmetica();
        criaListaDeTokensDeConcatenacaoString();

        when(lexer.tokenizar(sentencaDeclaracao)).thenReturn(listaDeTokensDeclaracao);
        when(lexer.tokenizar(sentencaAtribuicao)).thenReturn(listaDeTokensAtribuicao);
        when(lexer.tokenizar(sentencaOperacaoAritmetica)).thenReturn(listaDeTokensOperacaoAritmetica);
        when(lexer.tokenizar(sentencaConcatenacaoString)).thenReturn(listaDeTokensConcatenacaoString);

        when(identificadorDeToken.identifica("var")).thenReturn("PALAVRA_RESERVADA");
        when(identificadorDeToken.identifica("x")).thenReturn("IDV");
        when(identificadorDeToken.identifica("+")).thenReturn("ADICAO");
        when(identificadorDeToken.identifica("<>")).thenReturn("CONCATENACAO");
    }

    private void criaListaDeTokensDeConcatenacaoString() {
        listaDeTokensConcatenacaoString = new ArrayList<String>();
        listaDeTokensConcatenacaoString.add("x");
        listaDeTokensConcatenacaoString.add("=");
        listaDeTokensConcatenacaoString.add("\"abacaxi\"");
        listaDeTokensConcatenacaoString.add("<>");
        listaDeTokensConcatenacaoString.add("\"verde\"");
    }

    private void criaListaDeTokensDeOperacaoAritmetica() {
        listaDeTokensOperacaoAritmetica = new ArrayList<String>();
        listaDeTokensOperacaoAritmetica.add("x");
        listaDeTokensOperacaoAritmetica.add("=");
        listaDeTokensOperacaoAritmetica.add("1");
        listaDeTokensOperacaoAritmetica.add("+");
        listaDeTokensOperacaoAritmetica.add("1");
    }

    private void criaListaDeTokensDeDeclaracao() {
        listaDeTokensDeclaracao = new ArrayList<String>();
        listaDeTokensDeclaracao.add("var");
        listaDeTokensDeclaracao.add("x");
        listaDeTokensDeclaracao.add(":");
        listaDeTokensDeclaracao.add("String");
    }

    private void criaListaDeTokensDeAtribuicao() {
        listaDeTokensAtribuicao = new ArrayList<String>();
        listaDeTokensAtribuicao.add("x");
        listaDeTokensAtribuicao.add("=");
        listaDeTokensAtribuicao.add("1");
    }

    @Test
    public void gerenciadorDeValidacaoTokenizaUmaEntrada() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaDeclaracao);

        verify(lexer).tokenizar("var x : String");
    }

    @Test
    public void identificaAEntradaTokenizada() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaDeclaracao);

        verify(identificadorDeToken).identifica("var");
        verify(identificadorDeToken).identifica("x");
        verify(identificadorDeToken).identifica(":");
        verify(identificadorDeToken).identifica("String");
    }

    @Test
    public void chamaValidadorDeDeclaracaoDeVariavelSePrimeiroTokenForVar() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaDeclaracao);

        verify(identificadorDeToken).identifica("var");
        verify(validadorDeDeclaracaoDeVariavel).valida(listaDeTokensDeclaracao);
    }

    @Test
    public void chamaValidadorDeAtribuicaoSePrimeiroTokenForIDV() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaAtribuicao);

        verify(identificadorDeToken).identifica("x");
        verify(validadorDeAtribuicao).valida(listaDeTokensAtribuicao);
    }

    @Test
    public void chamaValidadorDeOperacoesAritmeticasSeContiverTokenOperadorMatematico() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaOperacaoAritmetica);

        verify(identificadorDeToken).identifica("+");
        verify(validadorDeOperacoesAritmeticas).valida(listaDeTokensOperacaoAritmetica);
    }

    @Test
    public void chamaValidadorDeConcatenacaoDeStringsSeContiverSimboloDeConcatenacao() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaConcatenacaoString);

        verify(identificadorDeToken).identifica("<>");
        verify(validadorDeConcatenacaoDeString).valida(listaDeTokensConcatenacaoString);
    }

    @Test
    public void chamaMensagemDeErroDeCadaValidadorERetornaOPrimeiroErro() throws Exception {

        when(validadorDeDeclaracaoDeVariavel.retornaMensagemErro()).thenReturn("1");
        when(validadorDeAtribuicao.retornaMensagemErro()).thenReturn("2");
        when(validadorDeOperacoesAritmeticas.retornaMensagemErro()).thenReturn("3");
        when(validadorDeConcatenacaoDeString.retornaMensagemErro()).thenReturn("4");

        gerenciadorDeValidacao.mostraMensagensDeErro();

        verify(validadorDeDeclaracaoDeVariavel).retornaMensagemErro();
        verify(validadorDeAtribuicao).retornaMensagemErro();
        verify(validadorDeOperacoesAritmeticas).retornaMensagemErro();
        verify(validadorDeConcatenacaoDeString).retornaMensagemErro();

        assertThat(gerenciadorDeValidacao.mostraMensagensDeErro().get(0), is("1"));
        assertThat(gerenciadorDeValidacao.mostraMensagensDeErro().get(1), is("2"));
        assertThat(gerenciadorDeValidacao.mostraMensagensDeErro().get(2), is("3"));
        assertThat(gerenciadorDeValidacao.mostraMensagensDeErro().get(3), is("4"));
    }

    @Test
    public void chamaMensagemDeErroIgnoraLinhasEmBrancoERetornaErros() throws Exception {

        when(validadorDeDeclaracaoDeVariavel.retornaMensagemErro()).thenReturn("");
        when(validadorDeAtribuicao.retornaMensagemErro()).thenReturn("2");
        when(validadorDeOperacoesAritmeticas.retornaMensagemErro()).thenReturn("");
        when(validadorDeConcatenacaoDeString.retornaMensagemErro()).thenReturn("4");

        gerenciadorDeValidacao.mostraMensagensDeErro();

        verify(validadorDeDeclaracaoDeVariavel).retornaMensagemErro();
        verify(validadorDeAtribuicao).retornaMensagemErro();
        verify(validadorDeOperacoesAritmeticas).retornaMensagemErro();
        verify(validadorDeConcatenacaoDeString).retornaMensagemErro();

        assertThat(gerenciadorDeValidacao.mostraMensagensDeErro().get(0), is("2"));
        assertThat(gerenciadorDeValidacao.mostraMensagensDeErro().get(1), is("4"));
    }
}