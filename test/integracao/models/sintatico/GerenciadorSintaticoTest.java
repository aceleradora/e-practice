package integracao.models.sintatico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GerenciadorSintaticoTest {
    @Test
    public void retornaMensagemDeErroQuandoInterpretaUmaExpressaoAritmeticaComAspasComoOperador() throws Exception {
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(identificadorDeToken);
        ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings = new ValidadorDeConcatenacaoDeStrings();
        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas();
        Lexer lexer = new Lexer();
        GerenciadorBuilder builder = new GerenciadorBuilder();
        GerenciadorSintatico gerenciadorSintatico = builder.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeAtribuicao)
                .com(validadorDeConcatenacaoDeStrings)
                .com(validadorDeDeclaracaoDeVariavel)
                .com(validadorDeOperacoesAritmeticas)
                .geraGerenciador();

        gerenciadorSintatico.interpreta("n = 2 \"\" 2");
        String resultado = gerenciadorSintatico.mostraMensagensDeErro();

        assertThat(resultado, is("Existem erros na expressão aritmetica."));
    }


}
