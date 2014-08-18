package unitario.model.Arvore;

import models.Arvore.PostFix;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EmptyStackException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestePostFix {

    ArrayList<String> tokens;
    PostFix posFix;
    ArrayList<String> espera;
    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<String>();
        posFix = new PostFix();
        espera = new ArrayList<>();
    }

    @Test
    public void dadoUmaOperacaoSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("+"); tokens.add("B"); tokens.add("*"); tokens.add("C");
        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("*"); espera.add("+");

        ArrayList resultado = posFix.criaPosfix(tokens);

        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacaoComParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("*"); tokens.add("("); tokens.add("B"); tokens.add("+"); tokens.add("C"); tokens.add(")");
        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("+"); espera.add("*");

        ArrayList resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoDuasOperacoesComParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("("); tokens.add("A"); tokens.add("+"); tokens.add("B"); tokens.add(")"); tokens.add("/"); tokens.add("(");
        tokens.add("C"); tokens.add("-"); tokens.add("D"); tokens.add(")");

        espera.add("A"); espera.add("B"); espera.add("+"); espera.add("C"); espera.add("D"); espera.add("-"); espera.add("/");

        ArrayList resultado = posFix.criaPosfix(tokens);

        assertEquals(resultado, espera);
    }

    @Test
    public void dadoDuasOperacoesComParentesesEUmaSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("("); tokens.add("A"); tokens.add("+"); tokens.add("B"); tokens.add(")"); tokens.add("/"); tokens.add("(");
        tokens.add("C"); tokens.add("-"); tokens.add("D"); tokens.add(")"); tokens.add("*"); tokens.add("E");

        espera.add("A"); espera.add("B"); espera.add("+"); espera.add("C"); espera.add("D"); espera.add("-"); espera.add("/");
        espera.add("E"); espera.add("*");

        ArrayList resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacaoComParentesesCercadaDeOperacoesSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("*"); tokens.add("("); tokens.add("B"); tokens.add("+"); tokens.add("C"); tokens.add(")");
        tokens.add("/"); tokens.add("D"); tokens.add("-"); tokens.add("E");

        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("+"); espera.add("*"); espera.add("D"); espera.add("/");
        espera.add("E"); espera.add("-");

        ArrayList resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacoesComQuatroParentesesAninhadosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("+"); tokens.add("("); tokens.add("B"); tokens.add("-"); tokens.add("("); tokens.add("C");
        tokens.add("+"); tokens.add("("); tokens.add("D"); tokens.add("-"); tokens.add("("); tokens.add("E"); tokens.add("+");
        tokens.add("F"); tokens.add(")"); tokens.add(")"); tokens.add(")"); tokens.add(")");

        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("D"); espera.add("E"); espera.add("F"); espera.add("+");
        espera.add("-"); espera.add("+"); espera.add("-"); espera.add("+");

        ArrayList resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacoesComCincoParentesesAninhadosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("*"); tokens.add("("); tokens.add("B"); tokens.add("+"); tokens.add("(");
        tokens.add("C"); tokens.add("*"); tokens.add("("); tokens.add("D"); tokens.add("+"); tokens.add("(");
        tokens.add("E"); tokens.add("*"); tokens.add("("); tokens.add("F"); tokens.add("+"); tokens.add("G");
        tokens.add(")"); tokens.add(")"); tokens.add(")"); tokens.add(")"); tokens.add(")");

        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("D"); espera.add("E"); espera.add("F"); espera.add("G");
        espera.add("+"); espera.add("*"); espera.add("+"); espera.add("*"); espera.add("+"); espera.add("*");

        ArrayList resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoCincoOperacoesComTresParentesesENumerosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("("); tokens.add("300"); tokens.add("+"); tokens.add("23"); tokens.add(")"); tokens.add("*"); tokens.add("(");
        tokens.add("43"); tokens.add("-"); tokens.add("21"); tokens.add(")"); tokens.add("/"); tokens.add("("); tokens.add("84");
        tokens.add("+"); tokens.add("7"); tokens.add(")");

        espera.add("300"); espera.add("23"); espera.add("+"); espera.add("43"); espera.add("21"); espera.add("-"); espera.add("*");
        espera.add("84"); espera.add("7"); espera.add("+"); espera.add("/");

        ArrayList resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado,espera);
    }

}
