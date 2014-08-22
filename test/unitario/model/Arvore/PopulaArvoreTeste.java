package unitario.model.Arvore;

import models.Arvore.Arvore;
import models.Arvore.Nodo;
import models.Arvore.PopulaArvore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PopulaArvoreTeste {
    @Test
    public void testaPopularUmaArvoreSimples() throws Exception {
        ArrayList<String> posFix = new ArrayList<String>();
        Nodo raiz = new Nodo("+");
        posFix.add("A");
        posFix.add("B");
        posFix.add("+");
        Arvore arvore = new PopulaArvore().populaArvore(posFix);

        assertThat(arvore.getRaiz().getToken(), is(raiz.getToken()));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(), is("A"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(), is("B"));
    }
}