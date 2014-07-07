package unitario.model;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

public class TesteValidadorDeAtribuicao {

    @Test
    public void quandoPrimeiraPalavraEUmIDVRetornaIDV() throws Exception {
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(0));

        assertThat(resultado, is("IDV"));
    }

    @Test
    public void quandoSegundaPalavraEUmOperadorIgualRetornaIgual() throws Exception {
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(1));

        assertThat(resultado, is("IGUAL"));

    }

    @Test
    public void quandoTerceiraPalavraEUmNumeroRetornaNumero() throws Exception {
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

        ArrayList<String> tokens = lexer.tokenizar("x = 1");

        String resultado = identificadorDeToken.identifica(tokens.get(2));

        assertThat(resultado, is("NUMERO"));

    }

    @Test
    public void quandoEscreveIdentificadorIGUALa1RetornaMensagemOk() throws Exception {
        String frase = "abacaxi = 1";
        Lexer lexer = new Lexer();
        IdentificadorDeToken tokenizer = new IdentificadorDeToken();
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(lexer, tokenizer);

        String resultado = validadorDeAtribuicao.validarAtribuicao(frase);

        assertThat(resultado, is("Código correto!"));

    }

    @Test
    public void quandoEscreve1IGUALaIdentificadorRetornaMensagemDeErro() throws Exception {
        String frase = "1 = abacaxi";
        Lexer lexer = new Lexer();
        IdentificadorDeToken tokenizer = new IdentificadorDeToken();
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(lexer, tokenizer);

        String resultado = validadorDeAtribuicao.validarAtribuicao(frase);

        assertThat(resultado, is("Erro de sintaxe!"));
    }

}
