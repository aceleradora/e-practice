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

public class ValidadorDeOperacoesAritmeticasTeste {

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
    public void dadaUmaOperacaoAritmeticaValidaVerificaSeOsIdentificadoresForamDeclaradosNaTabelaDeSimbolos() throws Exception {
        tabela.adicionaSimbolo("x","Inteiro");
        String declaracao = "x = 1 + 3";
        tokens = lexer.tokenizar(declaracao);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void dadaUmaOperacaoAritmeticaValidaVerificaIdentificadoresNaoDeclaradosNaTabelaDeSimbolos() throws Exception {
        tabela.adicionaSimbolo("y","Inteiro");
        String declaracao = "x = 1 + 3";
        tokens = lexer.tokenizar(declaracao);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(false));
    }

    @Test
    public void dadaUmaOperacaoAritmeticaValidaComMaisDeUmIndentificadorDeclaradoNaTabelaDeSimbolos() throws Exception {
        tabela.adicionaSimbolo("x","Inteiro");
        tabela.adicionaSimbolo("y","Inteiro");
        tabela.adicionaSimbolo("w","Inteiro");
        String declaracao = "x = y + 3 * w";
        tokens = lexer.tokenizar(declaracao);

        assertThat(validadorDeOperacoesAritmeticas.valida(tokens), is(true));
    }

    @Test
    public void dadaUmaOperacaoAritmeticaValidaComUmIndentificadorNaoDeclaradoNaTabelaDeSimbolosRetornaUmaMensagemDeErro() throws Exception {
        tabela.adicionaSimbolo("y","Inteiro");
        String declaracao = "x = 1 + 3";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is("A variável x não foi declarada."));
    }

    @Test
    public void dadaUmaOperacaoAritmeticaValidaComUmIndentificadorDeclaradoNaTabelaDeSimbolosNaoRetornaUmaMensagemDeErro() throws Exception {
        tabela.adicionaSimbolo("x","Inteiro");
        String declaracao = "x = 1 + 3";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    @Test
    public void dadaUmaOperacaoAritmeticaComUmIdvDeclaradoMasDoTipoStringRetornaMensagemErro() throws Exception {
        tabela.adicionaSimbolo("x","Inteiro");
        tabela.adicionaSimbolo("y","String");
        String declaracao = "x = y + 3";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is("A variável y não é do tipo Inteiro."));
    }

    @Test
    public void dadaUmaMultiplicacaoComUnarioRetornaUmaMensagemDeAcerto() throws Exception {
        tabela.adicionaSimbolo("resultado","Inteiro");
        String declaracao = "resultado=1*-2";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    @Test
    public void dadaUmaMultiplicacaoComDoisUnarioRetornaUmaMensagemDeAcerto() throws Exception {
        tabela.adicionaSimbolo("resultado","Inteiro");
        String declaracao = "resultado=-1*-2";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    @Test
    public void dadaUmaSomaComDoisUnarioRetornaUmaMensagemDeAcerto() throws Exception {
        tabela.adicionaSimbolo("resultado","Inteiro");
        String declaracao = "resultado=-1+-2";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    @Test
    public void dadaUmaSomaComDoisUnarioEntreParentesesRetornaUmaMensagemDeAcerto() throws Exception {
        tabela.adicionaSimbolo("resultado","Inteiro");
        String declaracao = "resultado=(-1)+(-2)";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

    @Test
    public void dadaUmaSomaComUnarioEntreParentesesComUmUnarioNegandoAExpressaoRetornaUmaMensagemDeAcerto() throws Exception {
        tabela.adicionaSimbolo("resultado","Inteiro");
        String declaracao = "resultado=-(-2+1)";
        tokens = lexer.tokenizar(declaracao);
        validadorDeOperacoesAritmeticas.valida(tokens);

        assertThat(validadorDeOperacoesAritmeticas.retornaMensagemErro(), is(""));
    }

}