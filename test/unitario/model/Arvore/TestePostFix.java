package unitario.model.Arvore;

import models.Arvore.PostFix;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestePostFix {

    ArrayList<String> tokens;
    PostFix luiz;
    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<String>();
        luiz = new PostFix();
    }

    @Test
    public void dadoUmaOperacaoSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");
        tokens.add("*");
        tokens.add("C");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("ABC*+"));
    }

    @Test
    public void dadoUmaOperacaoComParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A");
        tokens.add("*");
        tokens.add("(");
        tokens.add("B");
        tokens.add("+");
        tokens.add("C");
        tokens.add(")");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("ABC+*"));
    }

    @Test
    public void dadoDuasOperacoesComParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("(");
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");
        tokens.add(")");
        tokens.add("/");
        tokens.add("(");
        tokens.add("C");
        tokens.add("-");
        tokens.add("D");
        tokens.add(")");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("AB+CD-/"));
    }

    @Test
    public void dadoDuasOperacoesComParentesesEUmaSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("(");
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");
        tokens.add(")");
        tokens.add("/");
        tokens.add("(");
        tokens.add("C");
        tokens.add("-");
        tokens.add("D");
        tokens.add(")");
        tokens.add("*");
        tokens.add("E");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("AB+CD-/E*"));
    }

    @Test
    public void dadoUmaOperacaoComParentesesCercadaDeOperacoesSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A");
        tokens.add("*");
        tokens.add("(");
        tokens.add("B");
        tokens.add("+");
        tokens.add("C");
        tokens.add(")");
        tokens.add("/");
        tokens.add("D");
        tokens.add("-");
        tokens.add("E");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("ABC+*D/E-"));
    }

    @Test
    public void dadoUmaOperacoesComQuatroParentesesAninhadosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A");
        tokens.add("+");
        tokens.add("(");
        tokens.add("B");
        tokens.add("-");
        tokens.add("(");
        tokens.add("C");
        tokens.add("+");
        tokens.add("(");
        tokens.add("D");
        tokens.add("-");
        tokens.add("(");
        tokens.add("E");
        tokens.add("+");
        tokens.add("F");
        tokens.add(")");
        tokens.add(")");
        tokens.add(")");
        tokens.add(")");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("ABCDEF+-+-+"));
    }

    @Test
    public void dadoUmaOperacoesComCincoParentesesAninhadosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A");
        tokens.add("*");
        tokens.add("(");
        tokens.add("B");
        tokens.add("+");
        tokens.add("(");
        tokens.add("C");
        tokens.add("*");
        tokens.add("(");
        tokens.add("D");
        tokens.add("+");
        tokens.add("(");
        tokens.add("E");
        tokens.add("*");
        tokens.add("(");
        tokens.add("F");
        tokens.add("+");
        tokens.add("G");
        tokens.add(")");
        tokens.add(")");
        tokens.add(")");
        tokens.add(")");
        tokens.add(")");

        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("ABCDEFG+*+*+*"));
    }

}
