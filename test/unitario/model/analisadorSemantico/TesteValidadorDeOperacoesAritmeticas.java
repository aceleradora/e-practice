package unitario.model.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.ValidadorDeOperacoesAritmeticas;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        lexer = new Lexer();
        tabela = new TabelaDeSimbolos();
        validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas(tabela);
        IdentificadorDeToken indentificador = new IdentificadorDeToken();
        tokens = new ArrayList<String>();
    }

    @Test
    public void dadoUmArrayComUmaOperacaoAritmeticaValidaOsIdentificadoresEmRelacaoATabelaDeSimbolosRetornaTrue() throws Exception {

        tabela.adicionaSimbolo("x","Inteiro");
        String declaracao = "x = 1 + 3";
        tokens = lexer.tokenizar(declaracao);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));

    }

    @Test
    public void dadoUmArrayComUmaOperacaoAritmeticaValidaOsIdentificadoresEmRelacaoATabelaDeSimbolosRetornaFalse() throws Exception {

        tabela.adicionaSimbolo("y","Inteiro");
        String declaracao = "x = 1 + 3";
        tokens = lexer.tokenizar(declaracao);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));

    }

}
