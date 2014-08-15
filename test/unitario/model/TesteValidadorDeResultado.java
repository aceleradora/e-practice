package unitario.model;

import models.TabelaDeSimbolos;
import models.ValidadorDeResultado;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSemantico.ValidadorDeAtribuicao;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TesteValidadorDeResultado {
    private TabelaDeSimbolos tabelaDeSimbolos;
        Lexer lexer;
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
        ValidadorDeAtribuicao validadorDeAtribuicao;
        ValidadorDeResultado validadorDeResultado;
        ArrayList<String> tokensDeclaracao;
        ArrayList<String> tokensAtribuicao;
        String declaracao;
        String atribuicao;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
        lexer = new Lexer();
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos);
        tokensAtribuicao = new ArrayList<String>();
        tokensDeclaracao = new ArrayList<String>();
    }

    @Test
    public void retornaVerdadeiroQuandoVariavelXFoiDeclaradaComoVarresInteiroERecebeUmNumero() throws Exception {
        declaracao = "varres x : Inteiro\n";
        declaracao += "x = 1";
        tokensDeclaracao = lexer.tokenizar(declaracao);
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        validadorDeDeclaracaoDeVariavel.valida(tokensDeclaracao);

        ValidadorDeResultado validadorDeResultado = new ValidadorDeResultado(tabelaDeSimbolos);

        assertThat(validadorDeResultado.valida(tokensDeclaracao), is(true));
    }

    @Test
    public void retornaFalsoQuandoVariavelForDeclaradaComoVarresInteiroERecebeString() throws Exception {
        declaracao = "varres x : Inteiro";
        atribuicao = " x = \"abacaxi\"";
        tokensDeclaracao = lexer.tokenizar(declaracao);
        validadorDeDeclaracaoDeVariavel.valida(tokensDeclaracao);
        tokensAtribuicao = lexer.tokenizar(atribuicao);
        validadorDeAtribuicao.valida(tokensAtribuicao);

        assertThat(validadorDeResultado.valida(tokensDeclaracao), is(true));
        assertThat(validadorDeResultado.valida(tokensAtribuicao), is(false));
    }

}