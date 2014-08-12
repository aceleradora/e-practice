package unitario.model;

import models.ValidadorDeResultado;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TesteValidadorDeResultado {
    @Test
    public void retornaVerdadeiroQuandoFoiDeclaradoComoNumeroERecebeUmNumero() throws Exception {
        String solucao = "10";
        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado();

        assertTrue(validadorDeResultado.valida(solucao));
    }
}
