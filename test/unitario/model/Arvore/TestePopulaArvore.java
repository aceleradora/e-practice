package unitario.model.Arvore;

import models.Arvore.Arvore;
import models.Arvore.Nodo;
import models.Arvore.PopulaArvore;
import models.Arvore.PostFix;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestePopulaArvore {
    @Test
    public void testaPopularUmaArvoreSimples() throws Exception {
        Arvore arvore;
        PopulaArvore populaArvore = new PopulaArvore();
        PostFix postFix = new PostFix();
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");
        postFix.criaPosfix(tokens);
        ArrayList<String> postfix = postFix.getPostFix();
        arvore = populaArvore.populaArvore(postfix);
        Nodo raiz = new Nodo("+");
        assertThat(arvore.getRaiz().getToken(),is(raiz.getToken()));
        assertThat(postfix.get(0),is("A"));
        assertThat(postfix.get(1),is("B"));
        assertThat(postfix.get(2),is("+"));
        assertThat(arvore.getRaiz().getFilhos().get(0).getToken(),is("A"));
        assertThat(arvore.getRaiz().getFilhos().get(1).getToken(),is("B"));
    }
}
