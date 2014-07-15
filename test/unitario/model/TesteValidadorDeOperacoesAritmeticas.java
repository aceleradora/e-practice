package unitario.model;


import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import org.junit.*;
import scala.util.parsing.json.Lexer;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class TesteValidadorDeOperacoesAritmeticas {

    private Lexer lexer;
    private ArrayList<String> tokens;
    ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;

    @Before
    public void setUp() throws Exception {
        lexer = new Lexer();
        tokens = new ArrayList<String>();
        validadorDeOperacoesAritmeticas= new ValidadorDeOperacoesAritmeticas(tokens);
    }

    @Test
    public void dadaUmaOperacaoAritmeticaSemInsercaoDeParentesesDeveRetornarUmaMensagemOk() throws Exception {
        tokens.add("1");
        tokens.add("+");
        tokens.add("2");
        tokens.add("+");
        tokens.add("2");

        assertThat(validadorDeOperacoesAritmeticas.validarOperacoesAritmeticas(), is(true));
    }

    @Test
    public void dadoQueAOperacaoContenhaUmParenteseFechadoEAbertoEntaoTereiUmRetornoFalse() throws Exception {
        tokens.add(")");
        tokens.add("(");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeOsParentesesEstaoOk(), is(false));
    }

    @Test
    public void dadoQueTenhoUmParenteseAbertoTereiUmParenteFechadoParaEle() throws Exception {
        tokens.add("(");
        tokens.add("(");
        tokens.add(")");
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeOsParentesesEstaoOk(), is(true));    }

    @Test
    public void dadoQueTenhoAMesmaQuantidadeDeParentesesAbertosEFechadosRetornoAQuantidadeDeExprecoesComParenteses() throws Exception {
        tokens.add("(");
        tokens.add("(");
        tokens.add(")");
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.verificaQuantidadeExpressoesComParenteses(), is(2));
    }

    @Test
    public void dadoUmaOperacaoAritmeticaSemInsercaoDeParentesetaoTereiUmRetornoTrue() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add("+");
        tokens.add("5");
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.validarOperacoesAritmeticas(), is(true));
    }

    @Test
    public void dadoQueNaoFecheiOsParenteseCorretamenteEntaoRecebereiUmRetornoFalse() throws Exception {
        tokens.add("(");
        tokens.add("5");
        tokens.add(")");
        tokens.add(")");

        assertThat(validadorDeOperacoesAritmeticas.validarOperacoesAritmeticas(), is(false));
    }

}
