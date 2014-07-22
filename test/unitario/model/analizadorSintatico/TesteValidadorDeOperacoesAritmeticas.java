package unitario.model.analizadorSintatico;

import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import java.util.ArrayList;
import org.junit.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TesteValidadorDeOperacoesAritmeticas {

    private ArrayList<String> tokens;
    ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;

    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<String>();
        validadorDeOperacoesAritmeticas= new ValidadorDeOperacoesAritmeticas();
    }

    @Test
    public void validaUmaOperacaoComTodosOsOperadoresSemParenteses() throws Exception {
        criaTokensDeOperacaoSemParenteses();

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void parentesesInvertidos() throws Exception {
        tokens.add(")");
        tokens.add("7");
        tokens.add("(");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(false));
    }

    @Test
    public void naoValidaUmaExpressaoSemOperandosEOperadores() throws Exception {
        criaTokensDeExpressaoVazia();

        assertThat(validadorDeOperacoesAritmeticas.temExpressaoDentroDoParenteses(), is(false));
    }


    @Test
    public void validaUmaExpressaoQueContenhaApenasUmNumeroDentroDeParenteses() throws Exception {
        criaTokensDeExpressaoComUmOperandoDentroDeParenteses();

        assertThat(validadorDeOperacoesAritmeticas.temExpressaoDentroDoParenteses(), is(true));
    }


    @Test
    public void validaUmaExpressaoQueContenhaApenasUmNumero() throws Exception {
        tokens.add("5");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void naoValidaUmaExpressaoQueContenhaUmNumeroEUmOperadorAposEsseNumero() throws Exception {
        criaTokensDeExpressaoComUmNumeroEUmOperador();

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }


    @Test
    public void naoValidaUmaExpressaoQueContenhaUmOperadorEUmNumeroAposEsseOperador() throws Exception {
        tokens.add("+");
        tokens.add("5");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void naoValidaUmaExpressaoQueContenhaUmNumeroUmOperadorEUmParenteseAberto() throws Exception {
        tokens.add("5");
        tokens.add("+");
        tokens.add("(");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }


    @Test
    public void naoValidaUmaExpressaoQueContenhaUmNumeroEUmOperadorEntreParenteses() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void dadoQueTenhaUmaExpressaoDentroDosParenteseEntaoTereiUmRetornoTrue() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add("5");
        tokens.add(")");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.temExpressaoDentroDoParenteses(), is(true));
    }

    @Test
    public void dadoQueTenhaUmParenteseFechadoEAbertoEntaoTereiUmRetornoFalse() throws Exception {
        tokens.add(")");
        tokens.add("(");
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(false));
    }

    @Test
    public void dadoQueTenhaUmParenteseAbertoEumfechadoEntaoTereiUmRetornoTrue() throws Exception {
        criaTokensDeExpressaoVazia();

        assertThat(validadorDeOperacoesAritmeticas.aberturaEFechamentoDeParentesesEstaCorreta(), is(true));
    }

    @Test
    public void dadoQueAOperacaoContenhaSomenteParentesesAbertoEntaoTereiUmRetornoFalse() throws Exception {
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
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void validaOperacaoAritmeticaComOperadorMaisExpressaoEntreParentesesETudoEntreParenteses() throws Exception {
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
