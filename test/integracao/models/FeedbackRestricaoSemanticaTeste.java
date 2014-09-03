package integracao.models;

import models.GerenciadorDeFeedback;
import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.GerenciadorSemantico;
import models.analisadorSintatico.GerenciadorSintatico;
import models.analisadorSintatico.ValidadorDeConcatenacaoDeStrings;
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackRestricaoSemanticaTeste {

    @Mock private Exercicio exercicio;
    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private GerenciadorDeFeedback gerenciadorDeFeedback;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private String codigo;
    private ArrayList<String> resultadosDoUsuario = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {
        models.analisadorSintatico.GerenciadorBuilder builderSintatico = new models.analisadorSintatico.GerenciadorBuilder();
        models.analisadorSemantico.GerenciadorBuilder builderSemantico = new models.analisadorSemantico.GerenciadorBuilder();
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();
        tabelaDeSimbolos = new TabelaDeSimbolos();

        models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavelSintatico = new models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        models.analisadorSintatico.ValidadorDeAtribuicao validadorDeAtribuicaoSintatico = new models.analisadorSintatico.ValidadorDeAtribuicao(identificadorDeToken);
        models.analisadorSintatico.ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSintatico = new models.analisadorSintatico.ValidadorDeOperacoesAritmeticas();
        ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStringSintatico = new ValidadorDeConcatenacaoDeStrings();

        models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavelSemantico = new models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        models.analisadorSemantico.ValidadorDeAtribuicao validadorDeAtribuicaoSemantico = new models.analisadorSemantico.ValidadorDeAtribuicao(tabelaDeSimbolos);
        models.analisadorSemantico.ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSemantico = new models.analisadorSemantico.ValidadorDeOperacoesAritmeticas(tabelaDeSimbolos);
        models.analisadorSemantico.ValidadorDeConcatenacao validadorDeConcatenacaoDeStringSemantico = new models.analisadorSemantico.ValidadorDeConcatenacao(tabelaDeSimbolos);


        gerenciadorSintatico = builderSintatico.com(lexer)
                .com(identificadorDeToken)
                .com(validadorDeDeclaracaoDeVariavelSintatico)
                .com(validadorDeAtribuicaoSintatico)
                .com(validadorDeOperacoesAritmeticasSintatico)
                .com(validadorDeConcatenacaoDeStringSintatico)
                .geraGerenciador();

        gerenciadorSemantico = builderSemantico.com(validadorDeDeclaracaoDeVariavelSemantico)
                .com(validadorDeAtribuicaoSemantico)
                .com(validadorDeOperacoesAritmeticasSemantico)
                .com(validadorDeConcatenacaoDeStringSemantico)
                .geraGerenciador();

        quebradorDeCodigo = new QuebradorDeCodigoEmLinhas();

        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
    }

    @Test
    public void dadoQueDeclaroAMesmaVariavelDuasVezesDeveRetornarMensagemDeErro() throws Exception {
        codigo = "var x : String \n";
        codigo += "var x : String";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("A variável x ja foi declarada."));
    }

    @Test
    public void dadoQueDeclaroAsVariaveisCorretamenteDeveRetornarFeedbackPositivo() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("1");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        codigo = "varres x : Inteiro\n";
        codigo += "x = 1";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("Seu código está correto.\nSua resposta: 1 \n"));
    }

    @Test
    public void dadoQueDeclareiVariáveisDoTipoStringEDesejoFazerOperaçõesAritméticasDeveRetornarMensagemDeErro() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        codigo = "var x : String\n";
        codigo += "var y : String\n";
        codigo += "varres resultado: String\n";
        codigo += "resultado = x + y";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("A variável resultado não é do tipo Inteiro."));
    }

    @Test
    public void dadoQueDeclareiVariaveisDoTipoInteiroEDesejoFazerOperaçõesAritméticasDeveRetornarFeedbackPositivo() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("0");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        codigo = "var x: Inteiro\n";
        codigo += "var y: Inteiro\n";
        codigo += "varres resultado: Inteiro\n";
        codigo += "resultado = x + y\n";

        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("Seu código está correto.\nSua resposta: 0 \n"));
    }

    @Test
    public void dadoQueDeclareiVariaveisDoTipoInteiroEDesejoFazerOperaçõesDeConcatenacaoDeveRetornarMensagemDeErro() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        codigo = "var x: Inteiro\n";
        codigo += "var y: Inteiro\n";
        codigo += "varres resultado: Inteiro\n";
        codigo += "resultado = x <> y\n";

        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("A variável resultado não é do tipo String."));
    }

    @Test
    public void dadoQueTentoConcatenarUmInteiroComUmaStringReceboMensagemDeErro() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        codigo = "var x: Inteiro\n";
        codigo += "var y: String\n";
        codigo += "varres resultado: String\n";
        codigo += "resultado = x <> y\n";

        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("A variável x não é do tipo String."));
    }

    @Test
    public void dadoQueTentoConcatenarDuasVariaveisDoTipoStringReceboFeedbackPositivo() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        codigo = "var x: String\n";
        codigo += "var y: String\n";
        codigo += "varres resultado: String\n";
        codigo += "resultado = x <> y\n";

        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("Seu código está correto.\nSua resposta: \"\" \n"));
    }
}