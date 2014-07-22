package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorSemantico.GerenciadorSemantico;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {
    @Mock TabelaDeSimbolos tabelaDeSimbolos;
    private GerenciadorSemantico gerenciadorSemantico;

    @Before
    public void setUp() throws Exception {
        // ARRANGE ou DADO
        gerenciadorSemantico = new GerenciadorSemantico(tabelaDeSimbolos);
    }

    @Test
    public void adicionaVariavelNaTabelaDeSimbolos() throws Exception {
        gerenciadorSemantico.identificaDeclaracao("var x : Inteiro");

        verify(tabelaDeSimbolos).adicionaSimbolo("x", "Inteiro");
    }

    @Test
    public void verificaSeOSimboloJaExisteNaTabelaDeSimbolosAntesDeAdicionar() throws Exception {
        // ACT ou QUANDO
        gerenciadorSemantico.identificaDeclaracao("var umNumero : Inteiro");

        // ASSERT ou ENTÃO
        verify(tabelaDeSimbolos).temSimbolo("umNumero");
    }
}