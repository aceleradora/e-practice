package unitario.model;

import org.junit.Test;
import models.analisadorSintatico.ValidacaoAtribuicaoStrings;
import org.junit.Before;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TesteValidacaoAtribuicaoStrings {

    ValidacaoAtribuicaoStrings validador;
    ArrayList<String> entradaDoUsuario;

    @Before
    public void setUp() throws Exception {
        entradaDoUsuario = new ArrayList<String>();
    }

    @Test
    public void verificaSePrimeiroTokenEUmIdentificadorDeVariavel() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaPrimeiroToken(), "O primeiro token é uma variável.");
    }

    @Test
    public void seOPrimeiroTokenForUmaStringOValidaPrimeiroTokenRetornaUmaMensagemAvisando() throws Exception {
        entradaDoUsuario.add("\"variavel\"");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaPrimeiroToken(), "O primeiro token deveria ser uma variável.");
    }

    @Test
    public void seOPrimeiroTokenForUmNumeroOValidaPrimeiroTokenRetornaUmaMensagemAvisando() throws Exception {
        entradaDoUsuario.add("1");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaPrimeiroToken(), "O primeiro token deveria ser uma variável.");
    }

    @Test
    public void seOSegundoTokenForUmaAtribuicaoEntaoRetornaUmaMensagemDeSucesso() throws Exception {
        entradaDoUsuario.add("x");
        entradaDoUsuario.add("=");
        entradaDoUsuario.add("\"banana\"");
        validador = new ValidacaoAtribuicaoStrings(entradaDoUsuario);

        assertEquals(validador.validaSegundoToken(), "O segundo token é uma atribuição.");

    }
}
