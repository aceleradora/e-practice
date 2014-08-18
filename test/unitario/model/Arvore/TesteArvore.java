package unitario.model.Arvore;

import models.Arvore.Arvore;
import models.Arvore.Nodo;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by alunos4 on 11/08/14.
 */
public class TesteArvore {
    @Test
    public void seEuConstruirUmaÁrvoreElaTemUmNodoInicialNulo() throws Exception {
        Arvore arvore = new Arvore();
        Nodo raiz = arvore.getRaiz();
        assertNull(raiz);
    }
    @Test
    public void testaSeCriouNodoRaiz() throws Exception {
        Arvore arvore = new Arvore();
        Nodo raiz = new Nodo("raiz");
        arvore.setRaiz(raiz);
        assertNotNull(arvore.getRaiz());
    }
    @Test
    public void tentaAdicionarFilhoARaiz() throws Exception {
        Arvore arvore = new Arvore();
        Nodo raiz = new Nodo("raz");
        arvore.setRaiz(raiz);
        Nodo filho = new Nodo("filho");
        arvore.getRaiz().adicionaFilho(filho);
        assertNotNull(arvore.getRaiz().getFilhos());
        assertThat(arvore.getRaiz().getFilhos().size(),is(1));
    }
    @Test
    public void tentaAdicionarDoisFilhos() throws Exception {
        Arvore arvore = new Arvore();
        Nodo raiz = new Nodo("raz");
        arvore.setRaiz(raiz);
        Nodo filho = new Nodo("filho");
        Nodo irmao = new Nodo("irmão");
        arvore.getRaiz().adicionaFilho(filho);
        arvore.getRaiz().adicionaFilho(irmao);
        assertNotNull(arvore.getRaiz().getFilhos());
        assertThat(arvore.getRaiz().getFilhos().size(),is(2));
    }
    @Test
    public void tentaAdicionarFilhoAoFilhoDaRaiz() throws Exception {
        Arvore arvore = new Arvore();
        Nodo raiz = new Nodo("raz");
        arvore.setRaiz(raiz);
        Nodo filho = new Nodo("filho");
        arvore.getRaiz().adicionaFilho(filho);
        Nodo neto = new Nodo ("Neto");
        arvore.getRaiz().getFilho("filho").adicionaFilho(neto);
        assertNotNull(arvore.getRaiz().getFilho("filho").getFilhos());
        assertThat(arvore.getRaiz().getFilho("filho").getFilhos().size(), is(1));
    }
    @Test
    public void tentaCriarDuasArvores() throws Exception {
        Arvore arvoreUm = new Arvore();
        Arvore arvoreDois = new Arvore();
        assertNotNull(arvoreUm);
        assertNotNull(arvoreDois);
    }
    @Test
    public void tentaCriarDuasArvoresETestaSeElasSaoIguais() throws Exception {
        Arvore arvoreUm = new Arvore();
        Arvore arvoreDois = new Arvore();
        Nodo paiUm = new Nodo("paiUm");
        Nodo paiDois = new Nodo("paiDois");
        arvoreUm.setRaiz(paiUm);
        arvoreDois.setRaiz(paiDois);
        boolean comparaRaiz = arvoreUm.comparaRaiz(arvoreDois.getRaiz());
        assertThat(comparaRaiz, is(not(true)));
    }
    @Test
    public void tentaCompararFilhosDeArvoresDiferentes() throws Exception {
        Arvore arvoreUm = new Arvore();
        Arvore arvoreDois = new Arvore();
        Nodo paiUm = new Nodo("paiUm");
        Nodo paiDois = new Nodo("paiDois");
        arvoreUm.setRaiz(paiUm);
        arvoreDois.setRaiz(paiDois);
        Nodo filho11 = new Nodo("filho11");
        Nodo filho12 = new Nodo("filho12");
        Nodo filho13 = new Nodo("filho13");
        Nodo filho21 = new Nodo("filho21");
        Nodo filho22 = new Nodo("filho22");
        Nodo filho23 = new Nodo("filho23");
        arvoreUm.getRaiz().adicionaFilho(filho11);
        arvoreUm.getRaiz().adicionaFilho(filho12);
        arvoreUm.getRaiz().adicionaFilho(filho13);
        arvoreDois.getRaiz().adicionaFilho(filho21);
        arvoreDois.getRaiz().adicionaFilho(filho22);
        arvoreDois.getRaiz().adicionaFilho(filho23);
        boolean comparacao = arvoreUm.comparaArvore(arvoreDois);
        assertThat(comparacao,is(not(true)));
    }

}