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
    public void retornaTrueSeSegundaVariavelExiste() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        boolean resultado =  validador.verificaSeVariavelExiste(tokens.get(2));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSeTerceiraVariavelExiste() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");
        boolean resultado = validador.verificaSeVariavelExiste(tokens.get(4));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueSePrimeiraVariavelEhString() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        boolean resultado = validador.isString(tokens.get(0));

        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueSeSegundaVariavelEhString() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        boolean resultado = validador.isString(tokens.get(2));

        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueSeTerceiraVariavelEhString() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");
        boolean resultado = validador.isString(tokens.get(4));

        assertThat(resultado, is(true));
    }


    @Test
    public void quandoPrimeiraVariavelNaoExisteRetornaFalse() throws Exception {

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(false));

    }

    @Test
    public void quandoSegundaVariavelNaoExisteRetornaFalse() throws Exception {

        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(false));
    }

    @Test
    public void quandoTerceiraVariavelNaoExisteRetornaFalse() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(false));
    }

    @Test
    public void quandoTodasAsVariaveisExistemRetornaTrue() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(true));
    }

    @Test
    public void quandoPrimeiraVariavelNaoEhStringRetornaFalse() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "Inteiro");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");


        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(false));

    }

    @Test
    public void quandoSegundaVariavelNaoEhStringRetornaFalse() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "Inteiro");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(false));

    }

    @Test
    public void quandoTerceiraVariavelNaoEhStringRetornaFalse() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        tabelaDeSimbolos.adicionaSimbolo("verde", "Inteiro");

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(false));

    }

    @Test
    public void quandoAExpressaoEstaCorretaRetornaTrue() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(true));
    }


    @Test
    public void quandoAPrimeiraVariavelNaoExisteRetornaMensagemDeErro() throws Exception {

        validador.valida(tokens);

        String mensagem = validador.retornaMensagemErro();

        assertThat(mensagem, is("Erro: a variável " + tokens.get(0) + " não foi declarada."));
    }
}

