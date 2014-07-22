package unitario.model.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.GerenciadorSemantico;
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

    @Mock TabelaDeSimbolos tabelaDeSimbolos;
    @Mock Lexer lexer;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;

    private GerenciadorSemantico gerenciadorSemantico;
    private String declaracaoString;
    private ArrayList<String> listaDeTokensDeDeclaracaoDeString;

    @Before
    public void setUp() throws Exception {
        gerenciadorSemantico = new GerenciadorSemantico(tabelaDeSimbolos, lexer, validadorDeDeclaracao);
        declaracaoString = "var nome : String";
        listaDeTokensDeDeclaracaoDeString = new ArrayList<String>();
        criaListaDeTokensDeDeclaracaoDeString();

        when(lexer.tokenizar(declaracaoString)).thenReturn(listaDeTokensDeDeclaracaoDeString);
    }

    @Test
    public void tokenizaUmaLinhaDeEntrada() throws Exception {
        gerenciadorSemantico.interpreta(declaracaoString);

        verify(lexer).tokenizar(declaracaoString);
    }

    @Test
    public void chamaValidadorDeDeclaracaoQuandoPrimeiroTokenForVar() throws Exception {
        gerenciadorSemantico.interpreta(declaracaoString);

        verify(validadorDeDeclaracao).valida(listaDeTokensDeDeclaracaoDeString);
    }

    private void criaListaDeTokensDeDeclaracaoDeString() {
        listaDeTokensDeDeclaracaoDeString.add("var");
        listaDeTokensDeDeclaracaoDeString.add("nome");
        listaDeTokensDeDeclaracaoDeString.add(":");
        listaDeTokensDeDeclaracaoDeString.add("String");
    }
}