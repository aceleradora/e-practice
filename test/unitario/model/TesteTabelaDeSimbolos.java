package unitario.model;

import models.TabelaDeSimbolos;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TesteTabelaDeSimbolos {

    @Test
    public void quandoAtribuiInteiroAVarDoTipoInteiroRetornaTrue() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");

        boolean resultado = tabelaDeSimbolos.verificaSeTipoCombina("x", "Inteiro");

        assertThat(resultado, is(true));
    }

    @Test
    public void quandoAtribuiStringAVarDoTipoInteiroRetornaFalse() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");

        boolean resultado = tabelaDeSimbolos.verificaSeTipoCombina("x", "String");

        assertThat(resultado, is(false));

    }

    @Test
    public void retornaSeIDVExisteNaTabelaDeSimbolos() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");

        boolean existe = tabelaDeSimbolos.simboloExiste("x");

        assertThat(existe, is(true));
    }

    @Test
    public void retornaFalseCasoIDVNaoExistaNaTabelaDeSimbolos() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");

        boolean existe = tabelaDeSimbolos.simboloExiste("p");

        assertThat(existe, is(false));
    }

    @Test
    public void retornaTipoDoIDVCasoOIDVEstejaNaTabelaDeSimbolos() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");

        String tipoIDV = tabelaDeSimbolos.getTipoSimbolo("x");

        assertThat(tipoIDV, is("Inteiro"));
    }
}
