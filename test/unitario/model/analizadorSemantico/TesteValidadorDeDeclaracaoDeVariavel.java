package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by aluno6 on 21/07/14.
 */
public class TesteValidadorDeDeclaracaoDeVariavel {

    @Test
    public void verificaAExistenciaDeUmaVariavelQueJaFoiDeclaradaNaTabelaDeSimbolos() throws Exception {
        TabelaDeSimbolos tabela = new TabelaDeSimbolos();
        tabela.adicionaSimbolo("x", "Inteiro");
        String variavel = "x";
        ValidadorDeDeclaracaoDeVariavel validadoDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabela);

        boolean declarada = validadoDeDeclaracao.VerificaSeVariavelJaFoiDeclarada(variavel);

        assertThat(declarada, is(true));
    }
}
