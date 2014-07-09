package unitario.model;

import models.analisadorLexico.IdentificadorDeToken;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidacaoAtribuicaoStrings;
import org.junit.Before;
import static org.junit.Assert.*;

public class TesteValidacaoAtribuicaoStrings {

    private TabelaDeSimbolos tabela;

    private Lexer lexer;
    private String atribuicao;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
    }

    @Test
    public void verificaSeAtribuicaoFeitaFoiUmaString() throws Exception {
        IdentificadorDeToken token = new IdentificadorDeToken();
        atribuicao = token.identifica("\"teste\"");
        assertThat(atribuicao, is("CONSTANTE_TIPO_STRING"));

    }
}
