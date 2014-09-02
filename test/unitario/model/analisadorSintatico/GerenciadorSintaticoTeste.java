package unitario.model.analisadorSintatico;

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
public class GerenciadorSintaticoTeste {

    @Mock Lexer lexer;
    @Mock IdentificadorDeToken identificadorDeToken;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    @Mock ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeString;
    @Mock ValidadorGenerico validadorGenerico;

    private GerenciadorSintatico gerenciadorSintatico;
    private String sentencaDeclaracao;
    private ArrayList<String> listaDeTokensDeclaracao;
    private String sentencaAtribuicao;
    private ArrayList<String> listaDeTokensAtribuicao;
    private String sentencaAtribuicaoOperadorUnario;
    private ArrayList<String> listaDeTokensAtribuicaoOperadorUnario;
    private String sentencaOperacaoAritmetica;
    private ArrayList<String> listaDeTokensOperacaoAritmetica;
    private String sentencaConcatenacaoString;
    private ArrayList<String> listaDeTokensConcatenacaoString;

    @Before
    public void setUp() throws Exception {
        GerenciadorBuilder builder = new GerenciadorBuilder();

        gerenciadorSintatico = builder.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeAtribuicao)
                .com(validadorDeOperacoesAritmeticas)
                .com(validadorDeConcatenacaoDeString)
                .com(validadorGenerico)
                .geraGerenciador();

        sentencaDeclaracao = "var x : String";
        sentencaAtribuicao = "x = \"c\"";
        sentencaAtribuicaoOperadorUnario = "x = + 1";
        sentencaOperacaoAritmetica = "x = 1 + 1";
        sentencaConcatenacaoString = "x = \"abacaxi\" <> \"verde\"";

        criaListaDeTokensDeDeclaracao();
        criaListaDeTokensDeAtribuicao();
        criaListaDeTokensDeAtribuicaoOperadorUnario();
        criaListaDeTokensDeOperacaoAritmetica();
        criaListaDeTokensDeConcatenacaoString();

        when(lexer.tokenizar(sentencaDeclaracao)).thenReturn(listaDeTokensDeclaracao);
        when(lexer.tokenizar(sentencaAtribuicao)).thenReturn(listaDeTokensAtribuicao);
        when(lexer.tokenizar(sentencaAtribuicaoOperadorUnario)).thenReturn(listaDeTokensAtribuicaoOperadorUnario);
        when(lexer.tokenizar(sentencaOperacaoAritmetica)).thenReturn(listaDeTokensOperacaoAritmetica);
        when(lexer.tokenizar(sentencaConcatenacaoString)).thenReturn(listaDeTokensConcatenacaoString);


        when(identificadorDeToken.identifica("var")).thenReturn("PALAVRA_RESERVADA");
        when(identificadorDeToken.identifica("x")).thenReturn("IDV");
        when(identificadorDeToken.identifica("+")).thenReturn("ADICAO");
        when(identificadorDeToken.identifica("<>")).thenReturn("CONCATENACAO");
        when(identificadorDeToken.identifica("=")).thenReturn("IGUAL");
        when(identificadorDeToken.identifica("\"c\"")).thenReturn("CONSTANTE_TIPO_STRING");


        ArrayList<String> listaDeTokensGenerico = new ArrayList<String>();
        listaDeTokensGenerico.add("\"fdighiszhg\"");

        when(lexer.tokenizar("\"fdighiszhg\"")).thenReturn(listaDeTokensGenerico);
        when(identificadorDeToken.identifica("\"fdighiszhg\"")).thenReturn("CONSTANTE_TIPO_STRING");
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
        listaDeTokensAtribuicao.add("\"c\"");
    }

    private void criaListaDeTokensDeAtribuicaoOperadorUnario() {
        listaDeTokensAtribuicaoOperadorUnario = new ArrayList<String>();
        listaDeTokensAtribuicaoOperadorUnario.add("x");
        listaDeTokensAtribuicaoOperadorUnario.add("=");
        listaDeTokensAtribuicaoOperadorUnario.add("+");
        listaDeTokensAtribuicaoOperadorUnario.add("1");
    }

    @Test
    public void gerenciadorDeValidacaoTokenizaUmaEntrada() throws Exception {
        gerenciadorSintatico.interpreta(sentencaDeclaracao);

        verify(lexer).tokenizar("var x : String");
    }

    @Test
    public void identificaAEntradaTokenizada() throws Exception {
        gerenciadorSintatico.interpreta(sentencaDeclaracao);

        verify(identificadorDeToken).identifica("var");
        verify(identificadorDeToken).identifica("x");
        verify(identificadorDeToken).identifica(":");
        verify(identificadorDeToken).identifica("String");
    }

    @Test
    public void chamaValidadorDeDeclaracaoDeVariavelSePrimeiroTokenForVar() throws Exception {
        gerenciadorSintatico.interpreta(sentencaDeclaracao);

        verify(identificadorDeToken).identifica("var");
        verify(validadorDeDeclaracaoDeVariavel).valida(listaDeTokensDeclaracao);
    }

    @Test
    public void chamaValidadorDeAtribuicaoSePrimeiroTokenForIDV() throws Exception {
        gerenciadorSintatico.interpreta(sentencaAtribuicao);

        verify(identificadorDeToken).identifica("x");
        verify(validadorDeAtribuicao).valida(listaDeTokensAtribuicao);
    }

    @Test
    public void chamaValidadorDeAtribuicaoSeOTerceiroTokenForSinalPositivoOuNegativo() throws Exception {
        gerenciadorSintatico.interpreta(sentencaAtribuicaoOperadorUnario);

        verify(identificadorDeToken).identifica("+");
        verify(validadorDeAtribuicao).valida(listaDeTokensAtribuicaoOperadorUnario);

    }

    @Test
    public void chamaValidadorDeOperacoesAritmeticasSeContiverTokenOperadorMatematico() throws Exception {
        gerenciadorSintatico.interpreta(sentencaOperacaoAritmetica);

        verify(identificadorDeToken).identifica("+");
        verify(validadorDeOperacoesAritmeticas).valida(listaDeTokensOperacaoAritmetica);
    }

    @Test
    public void chamaValidadorDeConcatenacaoDeStringsSeContiverSimboloDeConcatenacao() throws Exception {
        gerenciadorSintatico.interpreta(sentencaConcatenacaoString);

        verify(identificadorDeToken).identifica("<>");
        verify(validadorDeConcatenacaoDeString).valida(listaDeTokensConcatenacaoString);
    }

    @Test
    public void chamaMensagemDeErroDeCadaValidadorERetornaOPrimeiroErro() throws Exception {
        when(validadorDeDeclaracaoDeVariavel.retornaMensagemErro()).thenReturn("1");

        gerenciadorSintatico.interpreta(sentencaDeclaracao);
        gerenciadorSintatico.mostraMensagensDeErro();

        verify(validadorDeDeclaracaoDeVariavel).retornaMensagemErro();

        assertThat(gerenciadorSintatico.mostraMensagensDeErro(), is("1"));
    }

    @Test
    public void chamaMensagemDeErroIgnoraLinhasEmBrancoERetornaErros() throws Exception {
        when(validadorDeAtribuicao.retornaMensagemErro()).thenReturn("2");

        gerenciadorSintatico.interpreta(sentencaAtribuicao);
        gerenciadorSintatico.mostraMensagensDeErro();

        verify(validadorDeAtribuicao).retornaMensagemErro();

        assertThat(gerenciadorSintatico.mostraMensagensDeErro(), is("2"));
    }

    @Test
      public void retornaMensagemDeErroQuandoDigitoAlgoSemNexo() throws Exception {
        ValidadorGenerico validador = new ValidadorGenerico();
        validadorGenerico = validador;

        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();
        gerenciadorSintatico = gerenciadorBuilder.com(identificadorDeToken)
                .com(lexer)
                .com(validadorDeAtribuicao)
                .com(validadorDeConcatenacaoDeString)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeOperacoesAritmeticas)
                .com(validadorGenerico)
                .geraGerenciador();

        gerenciadorSintatico.interpreta("\"fdighiszhg\"");

        assertThat(gerenciadorSintatico.mostraMensagensDeErro(), is("Código inválido.\nSintaxe não reconhecida."));
    }

    @Test
    public void retornaMensagemAcertoQuandoFacoUmaMultiplicacaoComUnario() throws Exception {

        ArrayList<String> listaDeTokensUnario = new ArrayList<String>();
        listaDeTokensUnario.add("resultado");
        listaDeTokensUnario.add("=");
        listaDeTokensUnario.add("1");
        listaDeTokensUnario.add("*");
        listaDeTokensUnario.add("+");
        listaDeTokensUnario.add("2");

        when(lexer.tokenizar("resultado=1*+2")).thenReturn(listaDeTokensUnario);
        when(identificadorDeToken.identifica("resultado")).thenReturn("IDV");
        when(identificadorDeToken.identifica("=")).thenReturn("IGUAL");
        when(identificadorDeToken.identifica("1")).thenReturn("NUMERO");
        when(identificadorDeToken.identifica("*")).thenReturn("MULTIPLICACAO");
        when(identificadorDeToken.identifica("+")).thenReturn("ADICAO");
        when(identificadorDeToken.identifica("2")).thenReturn("NUMERO");
        when(validadorDeOperacoesAritmeticas.valida(listaDeTokensUnario)).thenReturn(true);

        ValidadorDeOperacoesAritmeticas validador = new ValidadorDeOperacoesAritmeticas();
        validadorDeOperacoesAritmeticas = validador;

        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();
        gerenciadorSintatico = gerenciadorBuilder.com(identificadorDeToken)
                .com(lexer)
                .com(validadorDeAtribuicao)
                .com(validadorDeConcatenacaoDeString)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeOperacoesAritmeticas)
                .com(validadorGenerico)
                .geraGerenciador();

        String codigo = "resultado=1*+2";
        gerenciadorSintatico.interpreta(codigo);

        assertThat(gerenciadorSintatico.mostraMensagensDeErro(), is(""));
    }
}