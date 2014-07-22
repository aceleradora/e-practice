package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TesteValidadorDeConcatenacao {

    @Mock TabelaDeSimbolos tabelaDeSimbolos;

    @Test
    public void verificaTipoDaVariavel() throws Exception {
        ValidadorDeConcatenacao validador = new ValidadorDeConcatenacao(tabelaDeSimbolos);
        String variavel = "resultado";
        when(tabelaDeSimbolos.getTipoSimbolo("resultado")).thenReturn("String");

        validador.getTipoDeVariavel(variavel);

        verify(tabelaDeSimbolos).getTipoSimbolo(variavel);

    }

    @Test
    public void quandoVariavelNaoExisteRetornaFalse() throws Exception {
        ValidadorDeConcatenacao validador = new ValidadorDeConcatenacao(tabelaDeSimbolos);
        String variavel = "resultado";

        boolean resultado = validador.verificaSeVariavelExiste(variavel);

        assertThat(resultado, is(false));

    }

    @Test
    public void retornaTrueSeOTipoDaVariavelEhString() throws Exception {
        ValidadorDeConcatenacao validador = new ValidadorDeConcatenacao(tabelaDeSimbolos);
        String variavel = "x";
        when(tabelaDeSimbolos.getTipoSimbolo("x")).thenReturn("String");

        boolean resultado = validador.isString(variavel);

        assertThat(resultado, is(true));
    }
}
