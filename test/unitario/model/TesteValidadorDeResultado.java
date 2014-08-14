package unitario.model;

import models.TabelaDeSimbolos;
import models.ValidadorDeResultado;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TesteValidadorDeResultado {
    private TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
    }

    @Test
    public void retornaVerdadeiroQuandoVariavelXFoiDeclaradaComoVarresInteiroERecebeUmNumero() throws Exception {
        String solucao = "X = 10";
        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos);

        assertThat(validadorDeResultado.valida(solucao), is(true));
    }
}