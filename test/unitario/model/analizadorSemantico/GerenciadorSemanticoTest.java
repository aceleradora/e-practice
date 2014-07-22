package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorSemantico.GerenciadorSemantico;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {
    @Mock TabelaDeSimbolos tabelaDeSimbolos;
    private GerenciadorSemantico gerenciadorSemantico;

    @Before
    public void setUp() throws Exception {
        gerenciadorSemantico = new GerenciadorSemantico(tabelaDeSimbolos);

        when(tabelaDeSimbolos.temSimbolo("y")).thenReturn(true);
        when(tabelaDeSimbolos.temSimbolo("nome")).thenReturn(false).thenReturn(true);
    }

    @Test
    public void adicionaVariavelNaTabelaDeSimbolos() throws Exception {
        gerenciadorSemantico.identificaDeclaracao("var x : Inteiro");

        verify(tabelaDeSimbolos).adicionaSimbolo("x", "Inteiro");
    }

    @Test
    public void verificaSeOSimboloJaExisteNaTabelaDeSimbolosAntesDeAdicionar() throws Exception {
        gerenciadorSemantico.identificaDeclaracao("var umNumero : Inteiro");

        verify(tabelaDeSimbolos).temSimbolo("umNumero");
    }

    @Test
    public void naoAdicionaVariavelNaTabelaSeOSimboloJaExiste() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("y", "Inteiro");

        gerenciadorSemantico.identificaDeclaracao("var y : Inteiro");

        verify(tabelaDeSimbolos).temSimbolo("y");
        verify(tabelaDeSimbolos, times(1)).adicionaSimbolo("y", "Inteiro");
    }

    @Test
    public void validaAtribuicaoSeAVariavelExistirNaTabelaDeSimbolos() throws Exception {
        gerenciadorSemantico.identificaDeclaracao("var nome : String");
        boolean validacao = gerenciadorSemantico.validaAtribuicao("nome = \"Alejandro\"");

        verify(tabelaDeSimbolos, times(2)).temSimbolo("nome");
        assertThat(validacao, is(true));
    }
}