package unitario.model.analisadorSintatico;

import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import java.util.ArrayList;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ValidadorDeOperacoesAritmeticasTeste {

    private ArrayList<String> tokens;
    ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;

    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<String>();
        validadorDeOperacoesAritmeticas= new ValidadorDeOperacoesAritmeticas();
    }

    @Test
    public void retornaVerdadeiroQuandoValidaUmaOperacaoAritmeticaSemParenteses() throws Exception {
        criaTokensDeOperacaoSemParenteses();

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void retornaFalsoQuandoValidaUmaOperacaoComParentesesNaOrdemInvertida() throws Exception {
        tokens.add(")");
        tokens.add("7");
        tokens.add("(");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoVazia() throws Exception {
        criaTokensDeExpressaoVazia();

        assertThat(validadorDeOperacoesAritmeticas.temExpressaoDentroDoParenteses(), is(false));
    }


    @Test
    public void retornaVerdadeiroQuandoValidaUmaExpressaoQueContenhaApenasUmNumeroDentroDeParenteses() throws Exception {
        criaTokensDeExpressaoComUmOperandoDentroDeParenteses();

        assertThat(validadorDeOperacoesAritmeticas.temExpressaoDentroDoParenteses(), is(true));
    }


    @Test
    public void retornaVerdadeiroQuandoValidaUmaExpressaoQueContenhaApenasUmNumero() throws Exception {
        tokens.add("5");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoQueContenhaUmNumeroEUmOperadorAposEsseNumero() throws Exception {
        criaTokensDeExpressaoComUmNumeroEUmOperador();

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }


    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoQueContenhaUmOperadorQueNaoSejaSomaOuSubtracaoEUmNumeroAposEsseOperador() throws Exception {
        tokens.add("*");
        tokens.add("5");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoQueContenhaUmNumeroUmOperadorEUmParentesesAberto() throws Exception {
        tokens.add("5");
        tokens.add("+");
        tokens.add("(");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }


    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoQueContenhaUmNumeroEUmOperadorEntreParenteses() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void retornaVerdadeiroQuandoTemUmaExpressaoEntreParenteses() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add("5");
        tokens.add(")");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.temExpressaoDentroDoParenteses(), is(true));
    }

    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoQueSoContenhaUmParenteseFechadoEUmAberto() throws Exception {
        tokens.add(")");
        tokens.add("(");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(false));
    }

    @Test
    public void retornaVerdadeiroQuandoValidaUmaExpressaoQueTenhaUmParenteseAbertoEumFechado() throws Exception {
        criaTokensDeExpressaoVazia();

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(true));
    }

    @Test
    public void retornaFalsoQuandoValidaUmaExpressaoQueContenhaApenasDoisParentesesAbertos() throws Exception {
        tokens.add("(");
        tokens.add("(");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(false));
    }

    @Test
    public void dadoQueAOperacaoContenhaSomenteParentesesFechadoEntaoTereiUmRetornoFalse() throws Exception {
        tokens.add(")");
        tokens.add(")");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(false));
    }

    @Test
    public void dadoQueTenhoUmParenteseAbertoTereiUmParenteFechadoParaEle() throws Exception {
        tokens.add("(");
        tokens.add("(");
        tokens.add(")");
        tokens.add(")");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(true));
    }

    @Test
    public void dadoQueNaoFecheiOsParenteseCorretamenteEntaoRecebereiUmRetornoFalse() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add(")");
        tokens.add(")");
        tokens.add("(");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(false));
    }

    @Test
    public void dadoUmaOperacaoAritmeticaComInsercaoDeParentesetaoTereiUmRetornoTrue() throws Exception {
        tokens.add("(");
        tokens.add("(");
        tokens.add("2");
        tokens.add("+");
        tokens.add("2");
        tokens.add(")");
        tokens.add("/");
        tokens.add("2");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void validaOperacaoAritmeticaComOperadorMaisExpressaoEntreParentesesNaoEquilibradosEReceboMensagemDeErro() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add("(");
        tokens.add("4");
        tokens.add("/");
        tokens.add("2");
        tokens.add(")");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void validaOperacaoAritmeticaComOperadoMaisExpressaoEntreParenteses() throws Exception {
        tokens.add("5");
        tokens.add("+");
        tokens.add("(");
        tokens.add("4");
        tokens.add("-");
        tokens.add("2");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void validaOperacaoAritmeticaComExpressaoEntreParentesesMaisOperando() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add("5");
        tokens.add(")");
        tokens.add("+");
        tokens.add("2");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void validaOperacaoAritimeticaComDoisOperadoresAdjecentes() throws Exception {
        tokens.add("2");
        tokens.add("+");
        tokens.add("*");
        tokens.add("2");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void validaOperacaoAritmeticaComDuasExpressoesAmbasEntreParenteses() throws Exception {
        tokens.add("(");
        tokens.add("2");
        tokens.add("+");
        tokens.add("2");
        tokens.add(")");
        tokens.add("+");
        tokens.add("(");
        tokens.add("2");
        tokens.add("-");
        tokens.add("1");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void erroAoUsarConstantesStringsComoOperandos() throws Exception {
        tokens.add("2");
        tokens.add("+");
        tokens.add("2");
        tokens.add("+");
        tokens.add("\"10\"");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void validaExpressaoAritmeticaComDuasConstantesStrings() throws Exception {
        tokens.add("\"10\"");
        tokens.add("+");
        tokens.add("\"20\"");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void erroAoUsarSimboloDeConcatenacaoEmOperacoesAritimeticas() throws Exception {
        tokens.add("10");
        tokens.add("<>");
        tokens.add("20");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void erroAoUsarSimbolosTotalmenteErradosComOValidadorAritmetico() throws Exception {
        tokens.add("asrasr");
        tokens.add("zsfasrg");
        tokens.add("sdrhsd");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void erroAoUsarDoisOperadoresAritmeticosJuntos() throws Exception {
        tokens.add("5");
        tokens.add("++");
        tokens.add("5");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void naoValidaUmaExpressaoAritmeticaComDuasAspas() throws Exception {
        tokens.add("n");
        tokens.add("=");
        tokens.add("2");
        tokens.add("\"\"");
        tokens.add("2");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void retornaVerdadeiroQuandoValidaASomaDeDuasExpressoesEntreParenteses() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("(");
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add("3");
        tokens.add(")");
        tokens.add("+");
        tokens.add("(");
        tokens.add("2");
        tokens.add("-");
        tokens.add("3");
        tokens.add(")");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void retornaVerdadeiroQuandoValidaUmaExpressaoUnariaSimples() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("-");
        tokens.add("7");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void retornaVerdadeiroQuandoValidaMultiplicandoUmUnarioDentroDeParenteses() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("5");
        tokens.add("*");
        tokens.add("(");
        tokens.add("-");
        tokens.add("7");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void retornaVerdadeiroQuandoValidaSubtraindoUmUnarioDentroDeParenteses() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("5");
        tokens.add("-");
        tokens.add("(");
        tokens.add("-");
        tokens.add("7");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void deveEstarValidoUmaExpressaoQueContenhaUmaMultiplicacaoComUnario() throws Exception {
        tokens.add("1");
        tokens.add("*");
        tokens.add("-");
        tokens.add("2");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    @Test
    public void deveEstarValidoUmaExpressaoQueContenhaNumeroUmOperadorDeAdicaoENumeroUnario() throws Exception {
        tokens.add("5");
        tokens.add("+");
        tokens.add("+");
        tokens.add("6");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    private void criaTokensDeExpressaoVazia() {
        tokens.add("(");
        tokens.add(")");
        validadorDeOperacoesAritmeticas.valida(tokens);
    }

    private void criaTokensDeOperacaoSemParenteses() {
        tokens.add("1");
        tokens.add("+");
        tokens.add("2");
        tokens.add("/");
        tokens.add("2");
        tokens.add("*");
        tokens.add("2");
        tokens.add("-");
        tokens.add("6");
    }

    private void criaTokensDeExpressaoComUmNumeroEUmOperador() {
        tokens.add("5");
        tokens.add("+");
    }

    private void criaTokensDeExpressaoComUmOperandoDentroDeParenteses() {
        tokens.add("(");
        tokens.add("5");
        tokens.add(")");
        validadorDeOperacoesAritmeticas.valida(tokens);
    }
}