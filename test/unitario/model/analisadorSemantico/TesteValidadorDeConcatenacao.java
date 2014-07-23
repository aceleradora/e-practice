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
    public void retornaTrueSeTerceiroTokenExiste() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        boolean resultado =  validador.verificaSeVariavelExiste(tokens.get(2));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSeQuintoTokenExiste() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");
        boolean resultado = validador.verificaSeVariavelExiste(tokens.get(4));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSePrimeiroTokenEhString() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        boolean resultado = validador.isString(tokens.get(0));

        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueSeTerceiroTokenEhString() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        boolean resultado = validador.isString(tokens.get(2));

        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueSeQuintoTokenEhString() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");
        boolean resultado = validador.isString(tokens.get(4));

        assertThat(resultado, is(true));
    }


    @Test
    public void quandoPrimeiroTokenNaoExisteRetornaMensagemDeErro() throws Exception {

        String resultado = validador.valida(tokens);

        assertThat(resultado, is("A variável abacaxi não foi declarada."));

    }

    @Test
    public void quandoTerceiroTokenNaoExisteRetornaMensagemDeErro() throws Exception {

        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        String resultado = validador.valida(tokens);

        assertThat(resultado, is("A variável amarelo não foi declarada."));
    }

    @Test
    public void quandoQuintoTokenNaoExisteRetornaMensagemDeErro() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");

        String resultado = validador.valida(tokens);

        assertThat(resultado, is("A variável verde não foi declarada."));

    }
}

