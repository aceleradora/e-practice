package unitario.model;

import models.GerenciadorDeValidacao;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import java.util.ArrayList;

import models.analisadorSintatico.ValidadorDeAtribuicao;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TesteGerenciadorDeValidacao {

    @Mock Lexer lexer;
    @Mock IdentificadorDeToken identificadorDeToken;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;

    private String sentencaDeclaracao;
    private GerenciadorDeValidacao gerenciadorDeValidacao;
    private ArrayList<String> listaDeTokensDeclaracao;
    private String sentencaAtribuicao;
    private ArrayList<String> listaDeTokensAtribuicao;
    private String sentencaOperacaoAritmetica;
    private ArrayList<String> listaDeTokensOperacaoAritmetica;

    @Before
    public void setUp() throws Exception {
        gerenciadorDeValidacao = new GerenciadorDeValidacao(lexer, identificadorDeToken, validadorDeDeclaracaoDeVariavel, validadorDeAtribuicao, validadorDeOperacoesAritmeticas);
        sentencaDeclaracao = "var x : String";
        sentencaAtribuicao = "x = 1";
        sentencaOperacaoAritmetica = "x = 1 + 1";

        criaListaDeTokensDeAtribuicao();
        criaListaDeTokensDeDeclaracao();
        criaListaDeTokensDeOperacaoAritmetica();

        when(lexer.tokenizar(sentencaDeclaracao)).thenReturn(listaDeTokensDeclaracao);
        when(lexer.tokenizar(sentencaAtribuicao)).thenReturn(listaDeTokensAtribuicao);
        when(lexer.tokenizar(sentencaOperacaoAritmetica)).thenReturn(listaDeTokensOperacaoAritmetica);

        when(identificadorDeToken.identifica("var")).thenReturn("PALAVRA_RESERVADA");
        when(identificadorDeToken.identifica("x")).thenReturn("IDV");
        when(identificadorDeToken.identifica("+")).thenReturn("ADICAO");
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
}