package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorSemantico.GerenciadorSemantico;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {
    @Mock TabelaDeSimbolos tabelaDeSimbolos;

    @Test
    public void adicionaVariavelNaTabelaDeSimbolos() throws Exception {
        GerenciadorSemantico gerenciadorSemantico = new GerenciadorSemantico(tabelaDeSimbolos);
        gerenciadorSemantico.identificaDeclaracao("var x : Inteiro");

        verify(tabelaDeSimbolos).adicionaSimbolo("x", "Inteiro");
    }
}