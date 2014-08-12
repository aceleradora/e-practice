package unitario.model.Arvore;

import models.Arvore.ConstrutorDeArvore;
import models.SolucaoDoExercicio;
import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class TesteConstrutorDeArvore {
    QuebradorDeCodigoEmLinhas quebradorDeCodigoEmLinhas;
    SolucaoDoExercicio solucaoDoExercicio;
    @Mock
    TabelaDeSimbolos tabelaDeSimbolos;
    Lexer lexer = new Lexer();

    @Before
    public void setUp() throws Exception {

        quebradorDeCodigoEmLinhas = new QuebradorDeCodigoEmLinhas();


    }

    @Test
    public void quandoEuPassoUmaSolucaoDoExercicioParaoQuebradorDoCodigoEmLinhasDaArvoreEuReceboUmArrayListDeStrings() throws Exception {
        solucaoDoExercicio = new SolucaoDoExercicio("var x : Inteiro\nvarres resultado : Inteiro\nresultado = x*x");
        ConstrutorDeArvore construtorDeArvore = new ConstrutorDeArvore(quebradorDeCodigoEmLinhas,solucaoDoExercicio.getSolucaoDoUsuario(),tabelaDeSimbolos);
        ArrayList<String> linhas = construtorDeArvore.getLinhas();
        assertThat(linhas.get(0), is("var x : Inteiro"));
        assertThat(linhas.get(1),is("varres resultado : Inteiro"));
        assertThat(linhas.get(2),is("resultado = x*x"));
    }

    @Ignore
    @Test
    public void quandoEuEscolhoUmaLinhaParaEstruturarUmaArvoreEuCrioUmSubArrayParaCriarAArvore() throws Exception {


        solucaoDoExercicio = new SolucaoDoExercicio("var x : Inteiro\nvarres resultado : Inteiro\nresultado = x*x");
        ConstrutorDeArvore construtorDeArvore = new ConstrutorDeArvore(quebradorDeCodigoEmLinhas,solucaoDoExercicio.getSolucaoDoUsuario(),tabelaDeSimbolos);
        ArrayList<String> linhas = construtorDeArvore.getLinhas();
        assertThat(linhas.get(0), is("var x : Inteiro"));
        assertThat(linhas.get(1),is("varres resultado : Inteiro"));
        assertThat(linhas.get(2),is("resultado = x*x"));

        ArrayList<String> sublinha = construtorDeArvore.estruturaArvore(lexer.tokenizar(linhas.get(2)));
        assertThat(sublinha.size(),is(3));
        assertThat(sublinha.get(0),is("x"));
        assertThat(sublinha.get(1),is("*"));
        assertThat(sublinha.get(2),is("x"));
    }


}
