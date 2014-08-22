package unitario.model.Arvore;

import models.Arvore.Nodo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class TesteNodo {
    private Nodo nodo;

    @Before
    public void setUp() throws Exception {
        nodo = new Nodo("A");
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
        Nodo nodoPai = new Nodo("Pai");
        Nodo nodoFilho = new Nodo("Filho");

        nodoFilho.setPai(nodoPai);
        assertThat(nodoFilho.getPai(), is(nodoPai));
    }
}