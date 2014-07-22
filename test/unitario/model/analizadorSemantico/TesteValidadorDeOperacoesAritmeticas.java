package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import models.analisadorSemantico.ValidadorDeOperacoesAritmeticas;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

/**
 * Created by aluno6 on 22/07/14.
 */
public class TesteValidadorDeOperacoesAritmeticas {

    ArrayList<String> tokens;
    TabelaDeSimbolos tabela;
    Lexer lexer;
    ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;

    @Before
    public void setUp() throws Exception {
        Lexer lexer = new Lexer();
        tabela = new TabelaDeSimbolos();
        validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas();
        IdentificadorDeToken indentificador = new IdentificadorDeToken();
    }

/*    @Test
    public void dadoUmArrayComUmaOperacaoAritmeticaValidaRetornaTrue() throws Exception {

        String declaracao = "x = 2 + 3";
        ArrayList<String> tokens = lexer.tokenizar(declaracao);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));

    }*/
}
