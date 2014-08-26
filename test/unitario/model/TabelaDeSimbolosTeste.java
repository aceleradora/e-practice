package unitario.model;

import models.TabelaDeSimbolos;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TabelaDeSimbolosTeste {
    private TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");
    }

    @Test
    public void quandoAtribuiInteiroAVarDoTipoInteiroRetornaTrue() throws Exception {
        boolean resultado = tabelaDeSimbolos.verificaSeTipoCombina("x", "Inteiro");
        assertThat(resultado, is(true));
    }

    @Test
    public void quandoAtribuiStringAVarDoTipoInteiroRetornaFalse() throws Exception {
        boolean resultado = tabelaDeSimbolos.verificaSeTipoCombina("x", "String");
        assertThat(resultado, is(false));

    }

    @Test
    public void retornaSeIDVExisteNaTabelaDeSimbolos() throws Exception {
        boolean existe = tabelaDeSimbolos.simboloExiste("x");
        assertThat(existe, is(true));
    }

    @Test
    public void retornaFalseCasoIDVNaoExistaNaTabelaDeSimbolos() throws Exception {
        boolean existe = tabelaDeSimbolos.simboloExiste("p");
        assertThat(existe, is(false));
    }

    @Test
    public void retornaTipoDoIDVCasoOIDVEstejaNaTabelaDeSimbolos() throws Exception {
        String tipoIDV = tabelaDeSimbolos.getTipoSimbolo("x");
        assertThat(tipoIDV, is("Inteiro"));
    }

    @Test
    public void adicionaVariavelDoTipoInteiroComValorPadraoZero() throws Exception {
        String valor = tabelaDeSimbolos.getValor("x");
        assertThat(valor, is("0"));
    }

    @Test
    public void adicionaVariavelDoTipoStringComValorPadraoVazio() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("umaString", "String");
        String valor = tabelaDeSimbolos.getValor("umaString");
        assertThat(valor, is(""));
    }

    @Test
    public void podeAtualizarOValorDeUmaVariavelArmazenada() throws Exception {
        tabelaDeSimbolos.atualizaValor("x", "10");
        String valor = tabelaDeSimbolos.getValor("x");
        assertThat(valor, is("10"));
    }
}