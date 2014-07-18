package unitario.model;

import models.GerenciadorDeValidacao;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import java.util.ArrayList;

import models.analisadorSintatico.ValidadorDeAtribuicao;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
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

    private String sentenca;
    private GerenciadorDeValidacao gerenciadorDeValidacao;
    private ArrayList<String> listaDeTokens;
    private String sentencaAtribuicao;
    private ArrayList<String> listaDeTokensAtribuicao;

    @Before
    public void setUp() throws Exception {
        gerenciadorDeValidacao = new GerenciadorDeValidacao(lexer, identificadorDeToken, validadorDeDeclaracaoDeVariavel, validadorDeAtribuicao);
        sentenca = "var x : String";
        sentencaAtribuicao = "x = 1";

        listaDeTokensAtribuicao = new ArrayList<String>();
        listaDeTokensAtribuicao.add("x");
        listaDeTokensAtribuicao.add("=");
        listaDeTokensAtribuicao.add("1");

        listaDeTokens = new ArrayList<String>();
        listaDeTokens.add("var");
        listaDeTokens.add("x");
        listaDeTokens.add(":");
        listaDeTokens.add("String");

        when(lexer.tokenizar(sentenca)).thenReturn(listaDeTokens);
        when(lexer.tokenizar(sentencaAtribuicao)).thenReturn(listaDeTokensAtribuicao);
        when(identificadorDeToken.identifica("var")).thenReturn("PALAVRA_RESERVADA");
        when(identificadorDeToken.identifica("x")).thenReturn("IDV");
    }

    @Test
    public void gerenciadorDeValidacaoTokenizaUmaEntrada() throws Exception {
        gerenciadorDeValidacao.interpreta(sentenca);

        verify(lexer).tokenizar("var x : String");
    }

    @Test
    public void identificaAEntradaTokenizada() throws Exception {
        gerenciadorDeValidacao.interpreta(sentenca);

        verify(identificadorDeToken).identifica("var");
        verify(identificadorDeToken).identifica("x");
        verify(identificadorDeToken).identifica(":");
        verify(identificadorDeToken).identifica("String");
    }

    @Test
    public void chamaValidadorDeDeclaracaoDeVariavelSePrimeiroTokenForVar() throws Exception {
        gerenciadorDeValidacao.interpreta(sentenca);

        verify(identificadorDeToken).identifica("var");
        verify(validadorDeDeclaracaoDeVariavel).valida(listaDeTokens);
    }

    @Test
    public void chamaValidadorDeAtribuicaoSePrimeiroTokenForIDV() throws Exception {
        gerenciadorDeValidacao.interpreta(sentencaAtribuicao);

        verify(identificadorDeToken).identifica("x");
        verify(validadorDeAtribuicao).valida(listaDeTokensAtribuicao);

    }

    /*
    @Test
    public void quandoRecebeUmaStringComecandoComVarChamaOValidadorDeDeclaracao() throws Exception {
        String codigo = "var abacaxi : String";
        ArrayList<String> tokens = lexer.tokenizar(codigo);
        GerenciadorDeValidacao gerenciadorDeValidacao = new GerenciadorDeValidacao();

        boolean resultado = gerenciadorDeValidacao.verificaSeEhDeclaracao(tokens);

        assertThat(resultado, is(true));

    }

    @Test
    public void quandoRecebeUmaStringComecandoComIDVChamaOValidadorDeAtribuicao() throws Exception {
        String codigo = "x = 1";
        when(gerenciadorDeValidacao.verificaSeEhAtribuicao(codigo)).thenReturn(true);

        boolean resultado = gerenciadorDeValidacao.verificaSeEhAtribuicao(codigo);

        assertThat(resultado, is(true));

    }

    @Test
    public void quandoRecebeOperacaoAritmeticaChamaOValidadorDeOperacoesAritmeticas() throws Exception {
        String codigo = "1 + 1";
        ArrayList<String> tokens = lexer.tokenizar(codigo);
        when(gerenciadorDeValidacao.verificaSeEhOperacaoAritmetica(tokens)).thenReturn(true);

        boolean resultado = gerenciadorDeValidacao.verificaSeEhOperacaoAritmetica(tokens);

        assertThat(resultado, is(true));

    }

    @Test
    public void quandoRecebeAtribuicaoDeStringChamaOValidacaoDeAtribuicaoDeStrings() throws Exception {
        String codigo = "x = casa <> grande";

        when(gerenciadorDeValidacao.verificaSeEhAtribuicaoDeString(codigo)).thenReturn(true);

        boolean resultado = gerenciadorDeValidacao.verificaSeEhAtribuicaoDeString(codigo);

        assertThat(resultado, is(true));

    }

*/
}