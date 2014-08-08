package integracao.models.sintatico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GerenciadorSintaticoTest {

        GerenciadorSintatico gerenciadorSintatico;
    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private Lexer lexer;
    private GerenciadorBuilder builder;

    @Before
    public void setUp() throws Exception {

        builder = new GerenciadorBuilder();
        lexer = new Lexer();
        identificadorDeToken = new IdentificadorDeToken();
        tabelaDeSimbolos = new TabelaDeSimbolos();
        validadorDeAtribuicao = new ValidadorDeAtribuicao(identificadorDeToken);
        validadorDeConcatenacaoDeStrings = new ValidadorDeConcatenacaoDeStrings();
        validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas();
        gerenciadorSintatico = builder.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeAtribuicao)
                .com(validadorDeConcatenacaoDeStrings)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeOperacoesAritmeticas)
                .geraGerenciador();
    }

    @Test
    public void retornaMensagemDeErroQuandoInterpretaUmaExpressaoAritmeticaComAspasComoOperador() throws Exception {

        gerenciadorSintatico.interpreta("n = 2 \"\" 2");
        String resultado = gerenciadorSintatico.mostraMensagensDeErro();

        assertThat(resultado, is("Existem erros na expressão aritmetica."));
    }

    @Ignore
    @Test
    public void validaExpressaoInvalida() throws Exception {

        gerenciadorSintatico.interpreta("n = a \"\" b");
        String resultado = gerenciadorSintatico.mostraMensagensDeErro();

        assertThat(resultado, is("Existem erros."));
    }
}
