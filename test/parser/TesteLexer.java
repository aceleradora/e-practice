package parser;

import models.parser.Lexer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TesteLexer {

    private Lexer lexer;
    private ArrayList<String> tokens;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
    }

    @After
    public void tearDown() throws Exception {
        tokens.clear();
    }

    @Test
    public void dadoUmaLinhaEmBrancoDeveRetornarUmaListaDeTokensVazia() throws Exception {
        tokens = lexer.tokenizar("");
        assertTrue(tokens.isEmpty());
    }

    @Test
    public void dadaUmaLinhaComUmaUnicaPalavraDeveRetornarUmaListaComApenasUmToken() throws Exception {
        tokens = lexer.tokenizar("String");
        String firstToken = tokens.get(0);
        assertThat(tokens.size(), is(1));
        assertThat(firstToken, is("String"));
    }

    @Test
    public void dadaUmaLinhaComDuasPalavrasSeparadasPorEspacoDeveRetornarUmaListaComDoisTokens() throws Exception {
        tokens = lexer.tokenizar("String name");
        String firstToken = tokens.get(0);
        String secondToken = tokens.get(1);
        assertThat(tokens.size(), is(2));
        assertThat(firstToken, is("String"));
        assertThat(secondToken, is("name"));
    }

    @Test
    public void dadaUmaLinhaComPalavrasSeparadasPorEspacoDeveRetornarUmaListaComUmTokenParaCadaPalavra() throws Exception {
        tokens = lexer.tokenizar("var umNumero : Integer");
        assertThat(tokens.size(), is(4));
        assertThat(tokens.get(0), is("var"));
        assertThat(tokens.get(1), is("umNumero"));
        assertThat(tokens.get(2), is(":"));
        assertThat(tokens.get(3), is("Integer"));
    }

    @Test
    public void dadaUmaAtribuicaoSemEspacamentoDeveRetornarUmaListaComUmTokenParaCadaPalavra() throws Exception {
        tokens = lexer.tokenizar("var umNumero:Integer");
        assertThat(tokens.size(), is(4));
        assertThat(tokens.get(0), is("var"));
        assertThat(tokens.get(1), is("umNumero"));
        assertThat(tokens.get(2), is(":"));
        assertThat(tokens.get(3), is("Integer"));
    }
}
