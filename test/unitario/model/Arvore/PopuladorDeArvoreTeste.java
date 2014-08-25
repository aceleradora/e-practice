package unitario.model.Arvore;

import models.Arvore.Arvore;
import models.Arvore.PopuladorDeArvore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PopuladorDeArvoreTeste {
    @Test
    public void populaArvoreComUmaUnicaOperacao() throws Exception {
        ArrayList<String> posFix = new ArrayList<String>();
        posFix.add("A");
        posFix.add("B");
        posFix.add("+");
        Arvore arvore = new PopuladorDeArvore().populaArvoreAPartirDeUmPostFix(posFix);
        assertThat(arvore.getRaiz().getToken(), is("+"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(), is("A"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(), is("B"));
    }

    @Test
    public void populaUmaArvoreComUmUnicoRamoAPartirDeUmaExpressaoComDuasOperacoes() throws Exception {
        ArrayList<String> postFix = new ArrayList<String>();
        postFix.add("3");
        postFix.add("2");
        postFix.add("5");
        postFix.add("+");
        postFix.add("*");
        Arvore arvore = new PopuladorDeArvore().populaArvoreAPartirDeUmPostFix(postFix);

        assertThat(arvore.getRaiz().getToken(), is("*"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(), is("3"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(), is("+"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(0).getToken(), is("2"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getToken(), is("5"));
    }

    @Test
    public void populaUmaArvoreComUmUnicoRamoAPartirDeUmaExpressaoComTresOperacoes() throws Exception {
        ArrayList<String> postfix = new ArrayList<String>();
        postfix.add("3");
        postfix.add("2");
        postfix.add("5");
        postfix.add("1");
        postfix.add("+");
        postfix.add("-");
        postfix.add("*");
        Arvore arvore = new PopuladorDeArvore().populaArvoreAPartirDeUmPostFix(postfix);

        assertThat(arvore.getRaiz().getToken(), is("*"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(), is("3"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(), is("-"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(0).getToken(), is("2"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getToken(), is("+"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(0).getToken(), is("5"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getToken(), is("1"));
    }

    @Test
    public void populaUmaArvoreComDoisRamosAPartirDeUmaExpressaoComTresOperacoes() throws Exception {
        ArrayList<String> postfix = new ArrayList<String>();
        postfix.add("A");
        postfix.add("B");
        postfix.add("+");
        postfix.add("C");
        postfix.add("D");
        postfix.add("-");
        postfix.add("/");
        Arvore arvore = new PopuladorDeArvore().populaArvoreAPartirDeUmPostFix(postfix);

        assertThat(arvore.getRaiz().getToken(), is("/"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(), is("+"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(), is("-"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getFilhos().get(0).getToken(), is ("A"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getFilhos().get(1).getToken(), is ("B"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(0).getToken(), is ("C"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getToken(), is ("D"));
    }

    @Test
    public void populaUmaArvoreComUmRamoAPartirDeUmaExpressaoComSeisOperacoes() throws Exception {
        ArrayList<String> postfix = new ArrayList<String>();
        postfix.add("A");
        postfix.add("B");
        postfix.add("C");
        postfix.add("D");
        postfix.add("E");
        postfix.add("F");
        postfix.add("G");
        postfix.add("+");
        postfix.add("*");
        postfix.add("+");
        postfix.add("*");
        postfix.add("+");
        postfix.add("*");
        Arvore arvore = new PopuladorDeArvore().populaArvoreAPartirDeUmPostFix(postfix);

        assertThat(arvore.getRaiz().getToken(), is("*"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(), is("A"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(), is("+"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(0).getToken(), is ("B"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getToken(), is ("*"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(0).getToken(), is ("C"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getToken(), is ("+"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(0).getToken(), is ("D"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getToken(), is ("*"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(0).getToken(), is ("E"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getToken(), is ("+"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(0).getToken(), is ("F"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getFilhos().get(1).getToken(), is ("G"));

    }
}