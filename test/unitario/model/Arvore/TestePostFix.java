package unitario.model.Arvore;

import models.Arvore.PostFix;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by alunos4 on 12/08/14.
 */
public class TestePostFix {

    ArrayList<String> tokens;
    PostFix luiz;
    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<String>();
        luiz = new PostFix();
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");
        tokens.add("*");
        tokens.add("C");

    }

//    infix
//    A + B * C
//    A * (B + C)
//    (A + B) / (C – D )
//    (A + B) / (C – D) * E
//    A*(B+C)/D-E
//    A+(B-(C+(D-(E+F))))
//    A*(B+(C*(D+(E*(F+G)))))
//
//    posfix
//    A B C * +
//    A B C + *
//    A B + C D - /
//    A B + C D - / E *
//    ABC+*D/E-
//    ABCDEF+-+-+
//    ABCDEFG+*+*+*

    @Test
    public void dadoUmaOperacaoSemParentesesDeveRetornarUmaOperacaoPosFix() throws Exception {
        String resultado = luiz.criaPosfix(tokens);
        assertThat(resultado, is("ABC*+"));
    }
}
