package unitario.model;


import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import org.junit.*;
import scala.util.parsing.combinator.token.Tokens;
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
        tokens.add("(");
        tokens.add("1");
        tokens.add("+");
        tokens.add("2");


        assertThat(validadorDeOperacoesAritmeticas.validarOperacoesAritmeticasSemInsercaoDeParenteses(), is("INVALIDO"));
    }

    @Test
    public void dadoQueAOperacaoContenhaUmParenteseAberto() throws Exception {
        tokens.add("(");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeOperacaoContemUmParenteseAberto(), is("CONTEM"));
    }

    @Test
    public void dadoQueAOperacaoContenhaUmParenteseFechado() throws Exception {
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeOperacaoContemUmParenteseFechado(), is("CONTEM"));

    }

    @Test
    public void dadoQueAOperacaoContenhaUmParenteseAbertoEFechado() throws Exception {
        tokens.add(")");
        tokens.add("(");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeOperacaoContemUmParenteseAbertoEFechado(), is("CONTEM"));
    }

    @Test
    public void dadoQueOPrimeiroParenteseEncontradoEhUmParenteseAberto() throws Exception {
        tokens.add("(");
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.verificaSePrimeiroParenteseEncontradoEhUmParenteseAberto(), is("VALIDO"));
    }

    @Test
    public void dadoQueTenhoParentesesNaOperacaoENaoTemNadaEntreEles() throws Exception {
        tokens.add("(");
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeHaConteudoDentroDoParenteses(), is("NAO_CONTEM"));
    }

    @Test
    public void dadoQueTenhoUmParenteseAbertoTereiUmParenteFechadoParaEle() throws Exception {
        tokens.add("(");
        tokens.add("(");
        tokens.add(")");
        tokens.add(")");
        assertThat(validadorDeOperacoesAritmeticas.verificaSeOperacaoContemAMesmaQuandidadeDeParentesesAbertoEFechado(), is("CONTEM_MESMA_QUANTIDADE"));

    }
}
