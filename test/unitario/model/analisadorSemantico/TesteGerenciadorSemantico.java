package unitario.model.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorSemantico.*;
import models.analisadorSemantico.GerenciadorBuilder;
import models.analisadorSemantico.ValidadorDeAtribuicao;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import models.analisadorSemantico.ValidadorDeOperacoesAritmeticas;
import models.analisadorSintatico.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TesteGerenciadorSemantico {

    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    @Mock ValidadorDeConcatenacao validadorDeConcatenacao;
    @Mock ValidadorDeOperacoesAritmeticas validadorDeOperacaoAritmetica;


    private String declaracaoString;
    private String atribuicaoDeString;
    private String sentencaDeConcatenacao;
    private String sentencaDeOperacaoAritmeticaDeSoma;
    private String sentencaDeOperacaoAritmeticaDeSubtracao;
    private String sentencaDeOperacaoAritmeticaDeMultiplicacao;
    private String sentencaDeOperacaoAritmeticaDeDivisao;
    private ArrayList<String> listaDeTokensDeDeclaracaoDeString;
    private ArrayList<String> listaDeTokensDeAtribuicaoDeString;
    private ArrayList<String> listaDeTokensDeConcatenacao;
    private ArrayList<String> listaDeTokensDeOperacaoAritmeticaDeSoma;
    private ArrayList<String> listaDeTokensDeOperacaoAritmeticaDeSubtracao;
    private ArrayList<String> listaDeTokensDeOperacaoAritmeticaDeMultiplicacao;
    private ArrayList<String> listaDeTokensDeOperacaoAritmeticaDeDivisao;
    private GerenciadorSemantico gerenciadorSemantico;


    @Before
    public void setUp() throws Exception {
        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();

        gerenciadorSemantico = gerenciadorBuilder.com(validadorDeAtribuicao)
                .com(validadorDeDeclaracao)
                .com(validadorDeConcatenacao)
                .com(validadorDeOperacaoAritmetica)
                .geraGerenciador();

        declaracaoString = "var nome : String";
        atribuicaoDeString = "nome = \"alejandro\"";
        sentencaDeConcatenacao = "nome = \"João\" <> \"Henrique\"";
        sentencaDeOperacaoAritmeticaDeSoma = "x = 3 + 3";
        sentencaDeOperacaoAritmeticaDeSubtracao = "y = 5 - 3";
        sentencaDeOperacaoAritmeticaDeMultiplicacao = "z = 3 * 7";
        sentencaDeOperacaoAritmeticaDeDivisao = "a = 6 / 3";

        listaDeTokensDeDeclaracaoDeString = new ArrayList<String>();
        listaDeTokensDeAtribuicaoDeString = new ArrayList<String>();
        listaDeTokensDeConcatenacao = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmeticaDeSoma = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmeticaDeSubtracao = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmeticaDeMultiplicacao = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmeticaDeDivisao = new ArrayList<String>();

        criaListaDeTokensDeDeclaracaoDeString();
        criaListaDeTokensDeAtribuicaoDeString();
        criaListaDeTokensDeConcatenacao();
        criaListaDeTokensDeOperacaoAritmeticaDeSoma();
        criaListaDeTokensDeOperacaoAritmeticaDeSubtracao();
        criaListaDeTokensDeOperacaoAritmeticaDeMultiplicacao();
        criaListaDeTokensDeOperacaoAritmeticaDeDivisao();
    }

    private void criaListaDeTokensDeOperacaoAritmeticaDeDivisao() {
        listaDeTokensDeOperacaoAritmeticaDeDivisao.add("a");
        listaDeTokensDeOperacaoAritmeticaDeDivisao.add("=");
        listaDeTokensDeOperacaoAritmeticaDeDivisao.add("6");
        listaDeTokensDeOperacaoAritmeticaDeDivisao.add("/");
        listaDeTokensDeOperacaoAritmeticaDeDivisao.add("3");
    }

    private void criaListaDeTokensDeConcatenacao() {
        listaDeTokensDeConcatenacao.add("nome");
        listaDeTokensDeConcatenacao.add("=");
        listaDeTokensDeConcatenacao.add("\"João\"");
        listaDeTokensDeConcatenacao.add("<>");
        listaDeTokensDeConcatenacao.add("\"Henrique\"");
    }

    private void criaListaDeTokensDeDeclaracaoDeString() {
        listaDeTokensDeDeclaracaoDeString.add("var");
        listaDeTokensDeDeclaracaoDeString.add("nome");
        listaDeTokensDeDeclaracaoDeString.add(":");
        listaDeTokensDeDeclaracaoDeString.add("String");
    }

    private void criaListaDeTokensDeAtribuicaoDeString() {
        listaDeTokensDeAtribuicaoDeString.add("nome");
        listaDeTokensDeAtribuicaoDeString.add("=");
        listaDeTokensDeAtribuicaoDeString.add("\"alejandro\"");
    }

    private void criaListaDeTokensDeOperacaoAritmeticaDeSoma() {
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("x");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("=");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("3");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("+");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("3");
    }

    private void criaListaDeTokensDeOperacaoAritmeticaDeSubtracao() {
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("y");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("=");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("5");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("-");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("3");
    }

    private void criaListaDeTokensDeOperacaoAritmeticaDeMultiplicacao() {
        listaDeTokensDeOperacaoAritmeticaDeMultiplicacao.add("z");
        listaDeTokensDeOperacaoAritmeticaDeMultiplicacao.add("=");
        listaDeTokensDeOperacaoAritmeticaDeMultiplicacao.add("3");
        listaDeTokensDeOperacaoAritmeticaDeMultiplicacao.add("*");
        listaDeTokensDeOperacaoAritmeticaDeMultiplicacao.add("7");
    }

    @Test
    public void chamaValidadorDeDeclaracaoQuandoPrimeiroTokenForVar() throws Exception {
        gerenciadorSemantico.interpreta(declaracaoString);

        verify(validadorDeDeclaracao).valida(listaDeTokensDeDeclaracaoDeString);
    }

    @Test
    public void chamaValidadorDeAtribuicaoQuandoPrimeiroTokenForIDV() throws Exception {
        gerenciadorSemantico.interpreta(atribuicaoDeString);

        verify(validadorDeAtribuicao).valida(listaDeTokensDeAtribuicaoDeString);
    }

    @Test
    public void chamaValidadorDeConcatenacaoSeHouverOperadorDeConcatenacaoNaSentenca() throws Exception {
        gerenciadorSemantico.interpreta(sentencaDeConcatenacao);

        verify(validadorDeConcatenacao).valida(listaDeTokensDeConcatenacao);
    }

    @Test
    public void chamaValidadorDeOperacaoAritmeticaSeHouverOperadorSoma() throws Exception {
        gerenciadorSemantico.interpreta(sentencaDeOperacaoAritmeticaDeSoma);

        verify(validadorDeOperacaoAritmetica).valida(listaDeTokensDeOperacaoAritmeticaDeSoma);
    }

    @Test
    public void chamaValidadorDeOperacaoAritmeticaSeHouverOperadorSubtracao() throws Exception {
        gerenciadorSemantico.interpreta(sentencaDeOperacaoAritmeticaDeSubtracao);

        verify(validadorDeOperacaoAritmetica).valida(listaDeTokensDeOperacaoAritmeticaDeSubtracao);
    }

    @Test
    public void chamaValidadorDeOperacaoAritmeticaSeHouverOperadorMultiplicacao() throws Exception {
        gerenciadorSemantico.interpreta(sentencaDeOperacaoAritmeticaDeMultiplicacao);

        verify(validadorDeOperacaoAritmetica).valida(listaDeTokensDeOperacaoAritmeticaDeMultiplicacao);
    }

    @Test
    public void chamaValidadorDeOperacaoAritmeticaSeHouverOperadorDivisao() throws Exception {
        gerenciadorSemantico.interpreta(sentencaDeOperacaoAritmeticaDeDivisao);

        verify(validadorDeOperacaoAritmetica).valida(listaDeTokensDeOperacaoAritmeticaDeDivisao);
    }

    @Test
    public void chamaValidadorDeDeclaracaoComSentencaErradaERetornaAMensagemDeErro() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x","String");
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        validadorDeDeclaracao = validadorDeDeclaracaoDeVariavel;
        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();
        gerenciadorSemantico = gerenciadorBuilder.com(validadorDeAtribuicao)
                .com(validadorDeDeclaracao)
                .com(validadorDeConcatenacao)
                .com(validadorDeOperacaoAritmetica)
                .geraGerenciador();

        gerenciadorSemantico.interpreta("var x : String");

        assertThat(gerenciadorSemantico.mostraMensagensDeErro(), is("A variável "+"x"+" ja foi declarada."));
    }

    @Test
    public void chamaValidadorDeAtribuicaoComSentencaComVariavelNaoDeclaradaERetornaAMensagemDeErro() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        ValidadorDeAtribuicao validadorDeAtribuicao1 = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao = validadorDeAtribuicao1;
        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();
        gerenciadorSemantico = gerenciadorBuilder.com(validadorDeAtribuicao)
                .com(validadorDeDeclaracao)
                .com(validadorDeConcatenacao)
                .com(validadorDeOperacaoAritmetica)
                .geraGerenciador();

        gerenciadorSemantico.interpreta("x1 = \"lala\"");

        assertThat(gerenciadorSemantico.mostraMensagensDeErro(), is("A variável " + "x1" + " não foi declarada."));
    }

    @Test
    public void chamaValidadorDeAtribuicaoComSentencaComTiposIncompativeisERetornaAMensagemDeErro() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "String");
        ValidadorDeAtribuicao validadorDeAtribuicao1 = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao = validadorDeAtribuicao1;
        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();
        gerenciadorSemantico = gerenciadorBuilder.com(validadorDeAtribuicao)
                .com(validadorDeDeclaracao)
                .com(validadorDeConcatenacao)
                .com(validadorDeOperacaoAritmetica)
                .geraGerenciador();

        gerenciadorSemantico.interpreta("x = 1");

        assertThat(gerenciadorSemantico.mostraMensagensDeErro(), is("A Variavel "+"x"+" só aceita atribuição de valores do tipo "+"String"+"."));

    }
}