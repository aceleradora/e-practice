package unitario.model.calculadora;

import models.calculadora.CalculadoraDeResultado;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculadoraDeResultadoTeste {
    private ArrayList<String> postfix;
    private CalculadoraDeResultado calculadora;

    @Before
    public void setUp() throws Exception {
        postfix = new ArrayList<String>();
        calculadora = new CalculadoraDeResultado();
    }

    @Test
    public void retornaQuatroQuandoValidaOPostFixDoisDoisSoma() throws Exception {
        postfix.add("2");
        postfix.add("2");
        postfix.add("+");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(4));
    }

    @Test
    public void retornaTresQuandoValidaOPostFixUmDoisSoma() throws Exception {
        postfix.add("1");
        postfix.add("2");
        postfix.add("+");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(3));
    }

    @Test
    public void retorna21QuandoValidaOPostFixTresDoisCincoSomaMultiplicacao() throws Exception {
        postfix.add("3");
        postfix.add("2");
        postfix.add("5");
        postfix.add("+");
        postfix.add("*");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(21));
    }

    @Test
    public void retorna5QuandoValidaPostFixCincoCincoSomaSeteCincoSubtracaoDivisao() throws Exception {
        postfix.add("5");
        postfix.add("5");
        postfix.add("+");
        postfix.add("7");
        postfix.add("5");
        postfix.add("-");
        postfix.add("/");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(5));
    }
}