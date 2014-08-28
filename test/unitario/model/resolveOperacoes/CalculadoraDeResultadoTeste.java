package unitario.model.resolveOperacoes;

import models.TabelaDeSimbolos;
import models.resolveOperacoes.CalculadoraDeResultado;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalculadoraDeResultadoTeste {
    private ArrayList<String> postfix;
    private CalculadoraDeResultado calculadora;
    private TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        postfix = new ArrayList<String>();
        tabelaDeSimbolos = new TabelaDeSimbolos();
        calculadora = new CalculadoraDeResultado(tabelaDeSimbolos);
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

    @Test
    public void retornaTresQuandoValidaOPostFixXDoisSomaEAVariavelXTemValorUm() throws Exception {
        postfix.add("x");
        postfix.add("2");
        postfix.add("+");
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");
        tabelaDeSimbolos.atualizaValor("x", "1");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(3));
    }

    @Test
    public void retorna21QuanfdoValidaPostFixTresXYSomaMultiplicacaoEAVariavelXTemValor2EVariavelYTemValor5() throws Exception {
        postfix.add("3");
        postfix.add("x");
        postfix.add("y");
        postfix.add("+");
        postfix.add("*");
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");
        tabelaDeSimbolos.adicionaSimbolo("y", "Inteiro");
        tabelaDeSimbolos.atualizaValor("x", "2");
        tabelaDeSimbolos.atualizaValor("y", "5");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(21));
    }

    @Test
    public void retorna10QuandoValidaOPostFixZEAVariavelZTemValor10() throws Exception {
        postfix.add("z");
        tabelaDeSimbolos.adicionaSimbolo("z", "Inteiro");
        tabelaDeSimbolos.atualizaValor("z", "10");

        int resultado = calculadora.calculaOperacaoAPartirDoPostFix(postfix);
        assertThat(resultado, is(10));
    }
}