package unitario.model.analisadorSemantico;

import models.analisadorLexico.Lexer;
import models.analisadorSemantico.GerenciadorSemantico;
import models.analisadorSemantico.ValidadorDeAtribuicao;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {

    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;

    private String declaracaoString;
    private String atribuicaoDeString;
    private ArrayList<String> listaDeTokensDeDeclaracaoDeString;
    private ArrayList<String> listaDeTokensDeAtribuicaoDeString;
    private GerenciadorSemantico gerenciadorSemantico;

    @Before
    public void setUp() throws Exception {
        gerenciadorSemantico = new GerenciadorSemantico(validadorDeDeclaracao, validadorDeAtribuicao);
        declaracaoString = "var nome : String";
        atribuicaoDeString = "nome = \"alejandro\"";
        listaDeTokensDeDeclaracaoDeString = new ArrayList<String>();
        listaDeTokensDeAtribuicaoDeString = new ArrayList<String>();

        criaListaDeTokensDeDeclaracaoDeString();
        criaLIstaDeTokensDeAtribuicaoDeString();
    }

    private void criaListaDeTokensDeDeclaracaoDeString() {
        listaDeTokensDeDeclaracaoDeString.add("var");
        listaDeTokensDeDeclaracaoDeString.add("nome");
        listaDeTokensDeDeclaracaoDeString.add(":");
        listaDeTokensDeDeclaracaoDeString.add("String");
    }

    private void criaLIstaDeTokensDeAtribuicaoDeString() {
        listaDeTokensDeAtribuicaoDeString.add("nome");
        listaDeTokensDeAtribuicaoDeString.add("=");
        listaDeTokensDeAtribuicaoDeString.add("\"alejandro\"");
    }

    @Test
    public void chamaValidadorDeDeclaracaoQuandoPrimeiroTokenForVar() throws Exception {
        gerenciadorSemantico.interpreta(declaracaoString);

        verify(validadorDeDeclaracao).valida(listaDeTokensDeDeclaracaoDeString);
    }

    @Test
    public void chamaValidadorDeAtribuicaoQuandoPrimeiroTokenForIDV() throws Exception {
        gerenciadorSemantico.interpreta(atribuicaoDeString);

        verify(validadorDeAtribuicao).valida(listaDeTokensDeAtribuicaoDeString);
    }
}
