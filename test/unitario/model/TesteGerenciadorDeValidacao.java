package unitario.model;

import models.analisadorSintatico.*;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TesteGerenciadorDeValidacao {

    @Mock Lexer lexer;
    @Mock IdentificadorDeToken identificadorDeToken;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    @Mock
    ValidadorDeConcatenacaoDeStrings validadorDeAtribuicaoString;

    private String sentencaDeclaracao;
    private GerenciadorDeValidacao gerenciadorDeValidacao;
    private ArrayList<String> listaDeTokensDeclaracao;
    private String sentencaAtribuicao;
    private ArrayList<String> listaDeTokensAtribuicao;
    private String sentencaOperacaoAritmetica;
    private ArrayList<String> listaDeTokensOperacaoAritmetica;
    private String sentencaAtribuicaoString;
    private ArrayList<String> listaDeTokensAtribuicaoString;

    @Before
    public void setUp() throws Exception {
        GerenciadorBuilder builder = new GerenciadorBuilder();

        gerenciadorDeValidacao = builder.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeAtribuicao)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeOperacoesAritmeticas)
                .com(validadorDeAtribuicaoString)
                .geraGerenciador();

        sentencaDeclaracao = "var x : String";
        sentencaAtribuicao = "x = 1";
        sentencaOperacaoAritmetica = "x = 1 + 1";
        sentencaAtribuicaoString = "x = \"abacaxi\" ";

        criaListaDeTokensDeAtribuicaoString();
        criaListaDeTokensDeAtribuicao();
        criaListaDeTokensDeDeclaracao();
        criaListaDeTokensDeOperacaoAritmetica();

        when(lexer.tokenizar(sentencaDeclaracao)).thenReturn(listaDeTokensDeclaracao);
        when(lexer.tokenizar(sentencaAtribuicao)).thenReturn(listaDeTokensAtribuicao);
        when(lexer.tokenizar(sentencaOperacaoAritmetica)).thenReturn(listaDeTokensOperacaoAritmetica);
        when(lexer.tokenizar(sentencaAtribuicaoString)).thenReturn(listaDeTokensAtribuicaoString);

        when(identificadorDeToken.identifica("var")).thenReturn("PALAVRA_RESERVADA");
        when(identificadorDeToken.identifica("x")).thenReturn("IDV");
        when(identificadorDeToken.identifica("+")).thenReturn("ADICAO");
        when(identificadorDeToken.identifica("\"abacaxi\"")).thenReturn("CONSTANTE_TIPO_STRING");
    }

    private void criaListaDeTokensDeAtribuicaoString() {
        listaDeTokensAtribuicaoString = new ArrayList<String>();
        listaDeTokensAtribuicaoString.add("x");
        listaDeTokensAtribuicaoString.add("=");
        listaDeTokensAtribuicaoString.add("\"abacaxi\"");

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
    public void chamaValidadorDeAtribuicaoStringsSeContiverConstanteString() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaAtribuicaoString);

        verify(identificadorDeToken).identifica("\"abacaxi\"");
        verify(validadorDeAtribuicaoString).valida(listaDeTokensAtribuicaoString);


    }
}