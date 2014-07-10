package unitario.model;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

public class TesteValidadorDeAtribuicao {
    Lexer lexer;
    IdentificadorDeToken identificadorDeToken;
    ValidadorDeAtribuicao validadorDeAtribuicao;
    TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        identificadorDeToken = new IdentificadorDeToken();
        validadorDeAtribuicao = new ValidadorDeAtribuicao(lexer, identificadorDeToken);

    }


    @Test
    public void retornaTrueQuandoOPrimeirTokenForUmIDV() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        boolean resultado = validadorDeAtribuicao.validaPrimeiroToken(tokens.get(0));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoOSegundoTokenForUmOperadorIgual() throws Exception {

        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        boolean resultado = validadorDeAtribuicao.validaSegundoToken(tokens.get(1));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornaTrueQuandoTerceiroTokenForUmNumero() throws Exception {
        ArrayList<String> tokens = lexer.tokenizar("abacaxi = 1");
        boolean resultado = validadorDeAtribuicao.validaTerceiroToken(tokens.get(2));

        assertThat(resultado, is(true));
    }

    @Test
    public void retornatrueQuandoAVariavelParaQualOValorEstaSendoAtribuidoExisteNaTabelaDeSímbolos() throws Exception {
        validadorDeAtribuicao.setTabelaDeSimbolos(new TabelaDeSimbolos());
        tabelaDeSimbolos = validadorDeAtribuicao.getTabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("manga", "Inteiro");
        boolean resultado = validadorDeAtribuicao.validaIdv("manga = 1");
        assertThat(resultado, is(true));

    }

    @Test
    public void retornaTrueQuandoValidaALinha() throws Exception {
        validadorDeAtribuicao.setTabelaDeSimbolos(new TabelaDeSimbolos());
        tabelaDeSimbolos = validadorDeAtribuicao.getTabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("abacaqui", "Inteiro");
        boolean retorno = validadorDeAtribuicao.valida("abacaqui = 42");
        assertThat(retorno, is(true));

    }

    @Test
    public void retornaMensagemdeErroQuandoEuErroAlgumaRegraSintatica() throws Exception {
        String mensagem = validadorDeAtribuicao.mensagemDeErro("Abaxa = a");
        assertThat(mensagem, is(not("")));
    }

    @Test
    public void retornaMensagemDeErroQuandoEuErroOIgual() throws Exception {
        String mensagem = validadorDeAtribuicao.mensagemDeErro("Abaxa : 5");
        assertThat(mensagem, is("\nesperava \"=\" para atribuição de inteiros\n"));

    }
}


