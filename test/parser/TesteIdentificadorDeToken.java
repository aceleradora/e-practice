package parser;

import models.parser.IdentificadorDeToken;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TesteIdentificadorDeToken {

    private IdentificadorDeToken identificadorDeToken;
    private String tokenID;

    @Before
    public void setUp() throws Exception {
        identificadorDeToken = new IdentificadorDeToken();
    }

    @Test
    public void palavraVarDeveRetornarTipoDeTokenDeclaracaoDeVar() throws Exception {
        tokenID = identificadorDeToken.identifica("var");
        assertThat(tokenID, is("PALAVRA_RESERVADA"));
    }

    @Test
    public void palavraStringDeveRetornarTipoDeTokenTipoDeVariavel() throws Exception {
        tokenID = identificadorDeToken.identifica("String");
        assertThat(tokenID, is("TIPO_DE_VARIAVEL"));
    }

    @Test
    public void simboloDeIgualdadeDeveRetornarTipoDeTokenAtribuicao() throws Exception {
        tokenID = identificadorDeToken.identifica("=");
        assertThat(tokenID, is("IGUAL"));
    }

    @Test
    public void numeroDeveRetornarTipoDeTokenNumero() throws Exception {
        tokenID = identificadorDeToken.identifica("42");
        assertThat(tokenID, is("NUMERO"));
    }

    @Test
    public void comecandoPorNumeroETendoLetraNoMeioDeveRetornarErro() throws Exception {
        tokenID = identificadorDeToken.identifica("4abc2");
        assertThat(tokenID, is("ERRO"));
    }

    @Test
    public void palavraInteiroDeveRetornarTipoDeTokenTipoDeVariavel() throws Exception {
        tokenID = identificadorDeToken.identifica("Inteiro");
        assertThat(tokenID, is("TIPO_DE_VARIAVEL"));
    }

    @Test
    public void palavraIniciandoComLetraDeveRetornarIDV() throws Exception {
        tokenID = identificadorDeToken.identifica("abc_ABC_123");
        assertThat(tokenID, is("IDV"));
    }
}
