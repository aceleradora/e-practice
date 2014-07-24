package unitario.model.analisadorSemantico;

import models.analisadorSemantico.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {

    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    @Mock ValidadorDeConcatenacao validadorDeConcatenacao;
    @Mock ValidadorDeOperacoesAritmeticas validadorDeOperacaoAritmetica;

    private String declaracaoString;
    private String atribuicaoDeString;
    private String sentencaDeConcatenacao;
    private String sentencaDeOperacaoAritmeticaDeSoma;
    private String sentencaDeOperacaoAritmeticaDeSubtracao;
    private ArrayList<String> listaDeTokensDeDeclaracaoDeString;
    private ArrayList<String> listaDeTokensDeAtribuicaoDeString;
    private ArrayList<String> listaDeTokensDeConcatenacao;
    private ArrayList<String> listaDeTokensDeOperacaoAritmeticaDeSoma;
    private ArrayList<String> listaDeTokensDeOperacaoAritmeticaDeSubtracao;
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
        listaDeTokensDeDeclaracaoDeString = new ArrayList<String>();
        listaDeTokensDeAtribuicaoDeString = new ArrayList<String>();
        listaDeTokensDeConcatenacao = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmeticaDeSoma = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmeticaDeSubtracao = new ArrayList<String>();

        criaListaDeTokensDeDeclaracaoDeString();
        criaListaDeTokensDeAtribuicaoDeString();
        criaListaDeTokensDeConcatenacao();
        criaListaDeTokensDeOperacaoAritmetica();
        criaListaDeTokensDeOperacaoAritmeticaDeSubtracao();
    }

    private void criaListaDeTokensDeOperacaoAritmeticaDeSubtracao() {
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("y");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("=");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("5");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("-");
        listaDeTokensDeOperacaoAritmeticaDeSubtracao.add("3");
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

    private void criaListaDeTokensDeOperacaoAritmetica() {
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("x");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("=");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("3");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("+");
        listaDeTokensDeOperacaoAritmeticaDeSoma.add("3");

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
}