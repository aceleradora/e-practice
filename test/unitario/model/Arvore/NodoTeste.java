package unitario.model.Arvore;

import models.Arvore.Nodo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class NodoTeste {
    private Nodo nodo;
    private Nodo nodoPai;
    private Nodo nodoFilho;

    @Before
    public void setUp() throws Exception {
        nodo = new Nodo("A");
        nodoPai = new Nodo("Pai");
        nodoFilho = new Nodo("Filho");
    }

    @Test
    public void eInicializadoComPaiNulo() throws Exception {
        assertNull(nodo.getPai());
    }

    @Test
    public void eInicializadoSemFilhos() throws Exception {
        assertThat(nodo.getFilhos().size(), is(0));
    }

    @Test
    public void podeConterUmPai() throws Exception {
        Nodo nodoFilho = new Nodo("Filho");

        nodoFilho.setPai(nodoPai);
        assertThat(nodoFilho.getPai(), is(nodoPai));
    }

    @Test
    public void podeAdicionarUmFilho() throws Exception {
        nodoPai.adicionaFilho(nodoFilho);

        assertNotNull(nodoPai.getFilhos());
    }

    @Test
    public void podeCompararFilhos() throws Exception {
        Nodo segundoFilho = new Nodo("SegundoFilho");
        ArrayList<Nodo> listaDeFilhos = new ArrayList<Nodo>();
        listaDeFilhos.add(nodoFilho);
        listaDeFilhos.add(segundoFilho);

        nodoPai.adicionaFilho(nodoFilho);
        nodoPai.adicionaFilho(segundoFilho);
        assertTrue(nodoPai.comparaFilhos(listaDeFilhos));
    }
}