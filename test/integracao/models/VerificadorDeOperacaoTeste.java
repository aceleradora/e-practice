package integracao.models;

import models.GerenciadorDeFeedback;
import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.*;
import models.analisadorSintatico.*;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;
import models.analisadorSintatico.ValidadorGenerico;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VerificadorDeOperacaoTeste {
    private String codigo = "varres x : Inteiro\n" +
                            "varres y : Inteiro\n" +
                            "x = 1 + 3\n" +
                            "y = x + 2";

    private Lexer lexer = new Lexer();
    private TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
    private IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();
    private ValidadorDeAtribuicao validadorDeAtribuicaoSintatico = new ValidadorDeAtribuicao(identificadorDeToken);
    private QuebradorDeCodigoEmLinhas quebradorDeCodigoEmLinhas = new QuebradorDeCodigoEmLinhas();
    private models.analisadorSemantico.ValidadorDeAtribuicao validadorDeAtribuicaoSemantico = new models.analisadorSemantico.ValidadorDeAtribuicao(tabelaDeSimbolos);
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStringsSintatico = new ValidadorDeConcatenacaoDeStrings();
    private ValidadorDeConcatenacao validadorDeConcatenacaoSemantico = new ValidadorDeConcatenacao(tabelaDeSimbolos);
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavelSintatico = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
    private models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavelSemantico = new models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSintatico = new ValidadorDeOperacoesAritmeticas();
    private models.analisadorSemantico.ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSemantico = new models.analisadorSemantico.ValidadorDeOperacoesAritmeticas(tabelaDeSimbolos);
    private ValidadorGenerico validadorGenericoSintatico = new ValidadorGenerico();
    private models.analisadorSemantico.ValidadorGenerico validadorGenericoSemantico = new models.analisadorSemantico.ValidadorGenerico();
    private GerenciadorSintatico gerenciadorSintatico = new GerenciadorSintatico(lexer, identificadorDeToken, validadorDeDeclaracaoDeVariavelSintatico, validadorDeAtribuicaoSintatico, validadorDeOperacoesAritmeticasSintatico, validadorDeConcatenacaoDeStringsSintatico, validadorGenericoSintatico);
    private GerenciadorSemantico gerenciadorSemantico = new GerenciadorSemantico(validadorDeDeclaracaoDeVariavelSemantico, validadorDeAtribuicaoSemantico, validadorDeConcatenacaoSemantico, validadorDeOperacoesAritmeticasSemantico, validadorGenericoSemantico);

    private GerenciadorDeFeedback gerenciadorDeFeedback = new models.GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas, tabelaDeSimbolos);

    @Test
    public void retornaTantoETantoQuandoTenhoAsExpressoesTalETalNoCanvas() throws Exception {
        String s = gerenciadorDeFeedback.pegaFeedback();
        assertThat(s, is("Seu código está correto.\nResultado: 4 \nResultado: 6 \n"));
    }
}