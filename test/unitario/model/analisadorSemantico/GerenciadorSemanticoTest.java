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
    private String setencaDeOperacaoAritmetica;
    private ArrayList<String> listaDeTokensDeDeclaracaoDeString;
    private ArrayList<String> listaDeTokensDeAtribuicaoDeString;
    private ArrayList<String> listaDeTokensDeConcatenacao;
    private ArrayList<String> listaDeTokensDeOperacaoAritmetica;
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
        setencaDeOperacaoAritmetica = "x = 3 + 3";
        listaDeTokensDeDeclaracaoDeString = new ArrayList<String>();
        listaDeTokensDeAtribuicaoDeString = new ArrayList<String>();
        listaDeTokensDeConcatenacao = new ArrayList<String>();
        listaDeTokensDeOperacaoAritmetica = new ArrayList<String>();

        criaListaDeTokensDeDeclaracaoDeString();
        criaListaDeTokensDeAtribuicaoDeString();
        criaListaDeTokensDeConcatenacao();
        criaListaDeTokensDeOperacaoAritmetica();
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
        listaDeTokensDeOperacaoAritmetica.add("x");
        listaDeTokensDeOperacaoAritmetica.add("=");
        listaDeTokensDeOperacaoAritmetica.add("3");
        listaDeTokensDeOperacaoAritmetica.add("+");
        listaDeTokensDeOperacaoAritmetica.add("3");

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
    public void chamavaValidadorDeOperacaoAritmetica() throws Exception {
        gerenciadorSemantico.interpreta(setencaDeOperacaoAritmetica);

        verify(validadorDeOperacaoAritmetica).valida(listaDeTokensDeOperacaoAritmetica);
    }
}
