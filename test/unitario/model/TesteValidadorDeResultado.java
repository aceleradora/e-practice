package unitario.model;

import models.TabelaDeSimbolos;
import models.ValidadorDeResultado;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TesteValidadorDeResultado {
    private TabelaDeSimbolos tabelaDeSimbolos;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
    }

    @Test
    public void retornaVerdadeiroQuandoVariavelXFoiDeclaradaComoVarresInteiroERecebeUmNumero() throws Exception {
        String declaracao = "varres x : Inteiro\n";
        String solucao = "x = 10";
        Lexer lexer = new Lexer();
        ArrayList<String> tokens = lexer.tokenizar(declaracao);
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        validadorDeDeclaracao.valida(tokens);

        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos);

        assertThat(validadorDeResultado.valida(tokens), is(true));
    }
}