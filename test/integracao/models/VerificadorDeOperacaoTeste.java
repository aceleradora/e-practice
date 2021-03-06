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
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VerificadorDeOperacaoTeste {

    @Mock private Exercicio exercicio;
    private String codigo = "varres x : Inteiro\n" +
                            "varres y : Inteiro\n" +
                            "x = 1 + 3\n" +
                            "y = x + 2";

    private String codigo2 = "varres resultado : String\n" +
                             "resultado = \"casa\" <> \"velha\"\n";

    private String codigo3 = "varres resultado : String\n" +
                             "var x : String\n" +
                             "x = \"casa\"\n" +
                             "resultado = x <> \"velha\"\n";

    private String codigo4 = "var z : Inteiro\n" +
                             "varres y : Inteiro\n" +
                             "z = -10\n" +
                             "y = -z + 2";

    private String codigo5 = "var x : Inteiro\n" +
                             "varres y : Inteiro\n" +
                             "x = -10\n" +
                             "y = -x\n";

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
    private ArrayList<String> resultadosDoUsuario = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
    }

    @Test
    public void retornaOResultadoDaOperacaoQuandoTenhoUmaExpressaoAritmeticaNoCanvas() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("4");
        resultadosDoUsuario.add("6");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
        GerenciadorDeFeedback gerenciadorDeFeedback = new models.GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas, tabelaDeSimbolos, exercicio);
        String feedback = gerenciadorDeFeedback.pegaFeedback();
        assertThat(feedback, is("Seu código está correto.\nSua resposta: 4 \nSua resposta: 6 \n"));
    }

    @Test
    public void retornaAConcatenacaoDasStringsQuandoTenhoUmaExpressaoDeConcatenacaoNoCanvas() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"casavelha\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
        GerenciadorDeFeedback gerenciadorDeFeedback = new models.GerenciadorDeFeedback(codigo2, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas, tabelaDeSimbolos, exercicio);
        String feedback = gerenciadorDeFeedback.pegaFeedback();
        assertThat(feedback, is("Seu código está correto.\nSua resposta: \"casavelha\" \n"));
    }

    @Test
    public void retornaAConcatenacaoDasStringsQuandoTenhoUmaExpressaoDeConcatenacaoComUmaVariavelNoCanvas() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"casavelha\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
        GerenciadorDeFeedback gerenciadorDeFeedback = new models.GerenciadorDeFeedback(codigo3, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas, tabelaDeSimbolos, exercicio);
        String feedback = gerenciadorDeFeedback.pegaFeedback();
        assertThat(feedback, is("Seu código está correto.\nSua resposta: \"casavelha\" \n"));
    }

    @Test
    public void retorna12QuandoResolveExpressaoComUnario() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("12");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
        GerenciadorDeFeedback gerenciadorDeFeedback = new models.GerenciadorDeFeedback(codigo4, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas, tabelaDeSimbolos, exercicio);
        String feedback = gerenciadorDeFeedback.pegaFeedback();
        assertThat(feedback, is("Seu código está correto.\nSua resposta: 12 \n"));
    }

    @Test
    public void retorna10QuandoResolveExpressaoComUnario() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("10");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
        GerenciadorDeFeedback gerenciadorDeFeedback = new models.GerenciadorDeFeedback(codigo5, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas, tabelaDeSimbolos, exercicio);
        String feedback = gerenciadorDeFeedback.pegaFeedback();
        assertThat(feedback, is("Seu código está correto.\nSua resposta: 10 \n"));
    }
}