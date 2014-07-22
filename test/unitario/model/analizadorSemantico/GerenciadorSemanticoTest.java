package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.GerenciadorSemantico;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {

    @Mock TabelaDeSimbolos tabelaDeSimbolos;
    @Mock Lexer lexer;

    private GerenciadorSemantico gerenciadorSemantico;
    private String declaracaoString;

    @Before
    public void setUp() throws Exception {
        gerenciadorSemantico = new GerenciadorSemantico(tabelaDeSimbolos, lexer);
        declaracaoString = "var nome : String";
    }

    @Test
    public void tokenizaUmaLinhaDeEntrada() throws Exception {
        gerenciadorSemantico.interpreta(declaracaoString);

        verify(lexer).tokenizar(declaracaoString);
    }
}