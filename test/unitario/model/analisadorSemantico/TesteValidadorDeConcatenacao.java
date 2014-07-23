package unitario.model.analisadorSemantico;

import static org.mockito.Mockito.*;
import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorSemantico.ValidadorDeConcatenacao;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TesteValidadorDeConcatenacao {

    @Mock TabelaDeSimbolos mockTabelaDeSimbolos;
    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> tokens;
    ValidadorDeConcatenacao validador;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
        tokens = new ArrayList<String>();
        tokens.add("abacaxi");
        tokens.add("=");
        tokens.add("amarelo");
        tokens.add("<>");
        tokens.add("verde");
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");
        validador = new ValidadorDeConcatenacao(tabelaDeSimbolos);
    }

    @Test
    public void verificaTipoDaVariavel() throws Exception {
        ValidadorDeConcatenacao validador = new ValidadorDeConcatenacao(mockTabelaDeSimbolos);
        String variavel = "resultado";
        when(mockTabelaDeSimbolos.getTipoSimbolo("resultado")).thenReturn("String");

        validador.getTipoDeVariavel(variavel);

        verify(mockTabelaDeSimbolos).getTipoSimbolo(variavel);

    }

    @Test
    public void quandoVariavelNaoExisteRetornaFalse() throws Exception {
        ValidadorDeConcatenacao validador = new ValidadorDeConcatenacao(mockTabelaDeSimbolos);
        String variavel = "resultado";

        boolean resultado = validador.verificaSeVariavelExiste(variavel);

        assertThat(resultado, is(false));

    }

    @Test
    public void retornaTrueSeOTipoDaVariavelEhString() throws Exception {
        ValidadorDeConcatenacao validador = new ValidadorDeConcatenacao(mockTabelaDeSimbolos);
        String variavel = "x";
        when(mockTabelaDeSimbolos.getTipoSimbolo("x")).thenReturn("String");

        boolean resultado = validador.isString(variavel);

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSeIDVExiste() throws Exception {

        boolean resultado = validador.verificaSeVariavelExiste(tokens.get(0));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSeTerceiroTokenExiste() throws Exception {

        boolean resultado =  validador.verificaSeVariavelExiste(tokens.get(2));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSeQuintoTokenExiste() throws Exception {

        boolean resultado = validador.verificaSeVariavelExiste(tokens.get(4));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSePrimeiroTokenEhString() throws Exception {

        boolean resultado = validador.isString(tokens.get(0));

        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueSeTerceiroTokenEhString() throws Exception {

        boolean resultado = validador.isString(tokens.get(2));

        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueSeQuintoTokenEhString() throws Exception {

        boolean resultado = validador.isString(tokens.get(4));

        assertThat(resultado, is(true));
    }

    @Test
    public void quandoPrimeiroTokenNaoExisteRetornaMensagemDeErro() throws Exception {
        ArrayList<String> tokensInvalidos = new ArrayList<String>();
        tokensInvalidos.add("variavelInvalida");

        String resultado = validador.valida(tokensInvalidos);

        assertThat(resultado, is("O IDV não foi declarado."));

    }

    @Ignore
    @Test
    public void quandoTerceiroTokenNaoExisteRetornaMensagemDeErro() throws Exception {
        ArrayList<String> tokensInvalidos = new ArrayList<String>();
        tokensInvalidos.add("nome");

        String resultado = validador.valida(tokensInvalidos);

        assertThat(resultado, is("A variável nome não foi declarada."));

    }
}

