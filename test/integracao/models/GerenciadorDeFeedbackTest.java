package integracao.models;

import models.GerenciadorDeFeedback;
import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSintatico.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import scalaz.Alpha;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class GerenciadorDeFeedbackTest {


    private GerenciadorDeValidacao gerenciadorDeValidacao;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private GerenciadorDeFeedback gerenciadorDeFeedback;
    private TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        GerenciadorBuilder builder = new GerenciadorBuilder();
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();
        tabelaDeSimbolos = new TabelaDeSimbolos();

        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(identificadorDeToken);
        ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas();
        ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeString = new ValidadorDeConcatenacaoDeStrings();


        gerenciadorDeValidacao = builder.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeAtribuicao)
                .com(validadorDeOperacoesAritmeticas)
                .com(validadorDeConcatenacaoDeString)
                .geraGerenciador();

        quebradorDeCodigo = new QuebradorDeCodigoEmLinhas();
    }

    @Test
    public void dadoQueReceboUmaDeclaracaoDeVariavelInvalidaRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "var x = Inteiro";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(notNullValue()));
        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaDeclaracaoDeVariavelValidaRetornoStringVazia() throws Exception {
        String codigo = "var x : Inteiro";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesDeVariaveisRetornoMensagemDeErroVazia() throws Exception {
        String codigo = "var x : Inteiro\nvar y : Inteiro";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Test
    public void dadoQueReceboDuasAtribuicoesDeValoresNumericosValidosRetornoMensagemDeErroVazia() throws Exception {
        String codigo = "x = 4\ny = 5";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeStringsValidasRetornoUmaMensagemDeErroVazia() throws Exception {
        String codigo = "nome = \"Bernardo\"\n\"nome:\" <> \"José\"";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Test
    public void dadoQueReceboUmaOPeracaoDeAritmeticaValidaRetornoUmaMensagemDeErroVazia() throws Exception {
        String codigo = "numero = (2 + 2) * 5";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "var x = Inteiro\nvar \"erro\" : String";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao,quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboDuasAtribuicoesInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "x : 2\ny : +3";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboDuasOperacoesAritmeticasInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "numero = 2( +2";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeStringsValidasComUmEspacoAntesDasStringsRetornoUmaMensagemDeErroVazia() throws Exception {
        String codigo = " \"nome:\" <> \"José\"";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeNumerosInvalidaEntaoRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "10 <> 20";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeStringsUtilizandoSimboloMaiorEMenosValidasRetornoUmaMensagemDeErroVazia() throws Exception {
        String codigo = "\"nome:\" <> \"José\"";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(""));
    }

    @Ignore
    @Test
    public void dadoQueReceboUmaOperacaoAritmeticaInvalidaRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "numero = 2 + 2)";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Ignore
    @Test
    public void dadoQueReceboUmaOperacaoAritmeticaInvalidaUtilizandoDoisOperadoresJuntosRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "numero = 2 * + 2";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

}
