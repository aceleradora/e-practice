package unitario.model.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.ValidadorDeConcatenacao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ValidadorDeConcatenacaoTeste {

    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> tokens;
    ValidadorDeConcatenacao validador;
    Lexer lexer;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        tabelaDeSimbolos = new TabelaDeSimbolos();
        tokens = new ArrayList<String>();
        tokens.add("abacaxi");
        tokens.add("=");
        tokens.add("amarelo");
        tokens.add("<>");
        tokens.add("\"e\"");
        tokens.add("<>");
        tokens.add("verde");
        validador = new ValidadorDeConcatenacao(tabelaDeSimbolos);
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

        assertThat(mensagem, is("A variável " + tokens.get(0) + " não foi declarada."));
    }

    @Test
    public void quandoAPrimeiraVariavelNaoEhDoTipoStringRetornaMensagemDeErro() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "Inteiro");
        validador.valida(tokens);

        String mensagem = validador.retornaMensagemErro();

        assertThat(mensagem, is("A variável abacaxi não é do tipo String."));
    }

    @Test
    public void quandoASegundaVariavelNaoEhDoTipoStringRetornaMensagemDeErro() throws Exception {
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "Inteiro");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");

        validador.valida(tokens);

        String mensagem = validador.retornaMensagemErro();

        assertThat(mensagem, is("A variável amarelo não é do tipo String."));
    }

    @Test
    public void quandoTodasAsVariaveisExistemESaoStringsRetornaTrue() throws Exception {
        tokens.add("<>");
        tokens.add("azul");
        tokens.add("<>");
        tokens.add("vermelho");
        tabelaDeSimbolos.adicionaSimbolo("abacaxi", "String");
        tabelaDeSimbolos.adicionaSimbolo("amarelo", "String");
        tabelaDeSimbolos.adicionaSimbolo("verde", "String");
        tabelaDeSimbolos.adicionaSimbolo("azul", "String");
        tabelaDeSimbolos.adicionaSimbolo("vermelho", "String");

        boolean resultado = validador.valida(tokens);

        assertThat(resultado, is(true));
    }


}