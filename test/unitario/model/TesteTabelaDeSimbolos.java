package unitario.model;

import models.TabelaDeSimbolos;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
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
}
