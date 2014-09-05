package unitario.model.arvore;

import models.arvore.PostFix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PostFixTeste {
    private PostFix posFix;
    private ArrayList<String> tokens;
    private ArrayList<String> espera;
    private ArrayList<String> resultado;

    @Before
    public void setUp() throws Exception {
        posFix = new PostFix();
        tokens = new ArrayList<String>();
        espera = new ArrayList<String>();
        resultado = new ArrayList<String>();
    }

    @After
    public void tearDown() throws Exception {
        tokens.clear();
        espera.clear();
        resultado.clear();
    }

    @Test
    public void posfixaParaABSomaQuandoRecebeASomaB() throws Exception {
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");

        espera.add("A");
        espera.add("B");
        espera.add("+");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void posfixaParaABSubtracaoCSubtracaoQuandoRecebeAMenosBMenosC() throws Exception {
        tokens.add("A"); tokens.add("-"); tokens.add("B"); tokens.add("-");
        tokens.add("C");

        espera.add("A"); espera.add("B"); espera.add("-"); espera.add("C");
        espera.add("-");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(espera, resultado);
    }

    @Test
    public void dadoUmaOperacaoSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("+"); tokens.add("B"); tokens.add("*"); tokens.add("C");
        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("*"); espera.add("+");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacaoComParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("*"); tokens.add("("); tokens.add("B"); tokens.add("+"); tokens.add("C"); tokens.add(")");
        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("+"); espera.add("*");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoDuasOperacoesComParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("("); tokens.add("A"); tokens.add("+"); tokens.add("B"); tokens.add(")"); tokens.add("/"); tokens.add("(");
        tokens.add("C"); tokens.add("-"); tokens.add("D"); tokens.add(")");

        espera.add("A"); espera.add("B"); espera.add("+"); espera.add("C"); espera.add("D"); espera.add("-"); espera.add("/");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoDuasOperacoesComParentesesEUmaSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("("); tokens.add("A"); tokens.add("+"); tokens.add("B"); tokens.add(")"); tokens.add("/"); tokens.add("(");
        tokens.add("C"); tokens.add("-"); tokens.add("D"); tokens.add(")"); tokens.add("*"); tokens.add("E");

        espera.add("A"); espera.add("B"); espera.add("+"); espera.add("C"); espera.add("D"); espera.add("-"); espera.add("/");
        espera.add("E"); espera.add("*");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacaoComParentesesCercadaDeOperacoesSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("*"); tokens.add("("); tokens.add("B"); tokens.add("+"); tokens.add("C"); tokens.add(")");
        tokens.add("/"); tokens.add("D"); tokens.add("-"); tokens.add("E");

        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("+"); espera.add("*"); espera.add("D"); espera.add("/");
        espera.add("E"); espera.add("-");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoUmaOperacoesComQuatroParentesesAninhadosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("A"); tokens.add("+"); tokens.add("("); tokens.add("B"); tokens.add("-"); tokens.add("("); tokens.add("C");
        tokens.add("+"); tokens.add("("); tokens.add("D"); tokens.add("-"); tokens.add("("); tokens.add("E"); tokens.add("+");
        tokens.add("F"); tokens.add(")"); tokens.add(")"); tokens.add(")"); tokens.add(")");

        espera.add("A"); espera.add("B"); espera.add("C"); espera.add("D"); espera.add("E"); espera.add("F"); espera.add("+");
        espera.add("-"); espera.add("+"); espera.add("-"); espera.add("+");

        resultado = posFix.criaPosfix(tokens);
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

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado, espera);
    }

    @Test
    public void dadoCincoOperacoesComTresParentesesENumerosDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("("); tokens.add("300"); tokens.add("+"); tokens.add("23"); tokens.add(")"); tokens.add("*"); tokens.add("(");
        tokens.add("43"); tokens.add("-"); tokens.add("21"); tokens.add(")"); tokens.add("/"); tokens.add("("); tokens.add("84");
        tokens.add("+"); tokens.add("7"); tokens.add(")");

        espera.add("300"); espera.add("23"); espera.add("+"); espera.add("43"); espera.add("21"); espera.add("-"); espera.add("*");
        espera.add("84"); espera.add("7"); espera.add("+"); espera.add("/");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado,espera);
    }

    @Test
    public void dadoOperacaoComUnarioDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("1"); tokens.add("*"); tokens.add("-2");

        espera.add("1"); espera.add("-2"); espera.add("*");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado,espera);
    }

    @Test
    public void dadoOperacaoComUnarioDentroDeParentesesComAExpressaoNegadaDeveRetornarUmaOperacaoPosFix() throws Exception {
        tokens.add("-1"); tokens.add("*"); tokens.add("("); tokens.add("-2"); tokens.add("*"); tokens.add("4");tokens.add(")");

        espera.add("-1"); espera.add("-2"); espera.add("4"); espera.add("*"); espera.add("*");

        resultado = posFix.criaPosfix(tokens);
        assertEquals(resultado,espera);
    }
}