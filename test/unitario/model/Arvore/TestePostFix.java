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


//    infix
//    A*(B+C)/D-E dadoUmaOperacaoComParentesesCercadaDeOperacoesSemParentesesDeveRetornarUmaOperacaoPosFix
//    A+(B-(C+(D-(E+F)))) dadoUmaOperacoesComQuatroParentesesAninhadosDeveRetornarUmaOperacaoPosFix
//    A*(B+(C*(D+(E*(F+G))))) dadoUmaOperacoesComCincoParentesesAninhadosDeveRetornarUmaOperacaoPosFix
//
//    posfix
//    ABC+*D/E-
//    ABCDEF+-+-+
//    ABCDEFG+*+*+*

}
