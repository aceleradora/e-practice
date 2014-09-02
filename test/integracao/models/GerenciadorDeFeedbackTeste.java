package integracao.models;

import models.GerenciadorDeFeedback;
import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.GerenciadorSemantico;
import models.analisadorSintatico.*;
import models.exercicioProposto.Exercicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorDeFeedbackTeste {

    @Mock
    private Exercicio exercicio;
    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private GerenciadorDeFeedback gerenciadorDeFeedback;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ArrayList<String> resultadosDoUsuario = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {
        models.analisadorSintatico.GerenciadorBuilder builderSintatico = new GerenciadorBuilder();
        models.analisadorSemantico.GerenciadorBuilder builderSemantico = new models.analisadorSemantico.GerenciadorBuilder();
        Lexer lexer = new Lexer();
        IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();
        tabelaDeSimbolos = new TabelaDeSimbolos();

        ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavelSintatico = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        ValidadorDeAtribuicao validadorDeAtribuicaoSintatico = new ValidadorDeAtribuicao(identificadorDeToken);
        ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSintatico = new ValidadorDeOperacoesAritmeticas();
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
    public void dadoQueReceboUmaDeclaracaoDeVariavelInvalidaRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "var x = Inteiro";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(notNullValue()));
        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaDeclaracaoDeVariavelValidaRetornoUmaMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"queijo\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : String\nvar x : Inteiro\nz=\"queijo\"\n";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: \"queijo\" \n"));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesDeVariaveisRetornoMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("0");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : Inteiro\nvar x : Inteiro\nvar y : Inteiro\nz = x";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: 0 \n"));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesDeStringsValidasComEspacoNoInicioDaDeclaracaoRetornoMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"resultado\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : String\n var x : String \n var y: String\nz = \"resultado\"";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: \"resultado\" \n"));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesDeVariaveisInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\n var x: Sting \n vars y: intiro";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not(containsString("Seu código está correto."))));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesDeVariaveisInvalidasComOTipoMinusculoRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nvar x : Inteiro\nvar y: inteiro";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not(containsString("Seu código está correto."))));
    }

    @Test
    public void dadoQueReceboDuasAtribuicoesDeValoresNumericosValidosRetornoMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("5");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : Inteiro\nvar x : Inteiro\nvar y : Inteiro\nx = 4\ny = 5\nz = y";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: 5 \n"));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeStringsValidasRetornoUmaMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"BernardoJosé\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : String\n" +
                "var nome : String\n" +
                "nome = \"Bernardo\"\n" +
                "nome = nome <> \"José\"\n" +
                "z = nome";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: \"BernardoJosé\" \n"));
    }

    @Test
    public void dadoQueReceboUmaOPeracaoDeAritmeticaValidaRetornoUmaMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("20");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : Inteiro\nvar numero : Inteiro\n" +
                "numero = (2 + 2) * 5\nz = numero";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: 20 \n"));
    }

    @Test
    public void dadoQueReceboDuasDeclaracoesInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nvar x = Inteiro\nvar \"erro\" : String";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboDuasAtribuicoesInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nx : 2\ny : +3";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboDuasOperacoesAritmeticasInvalidasRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nnumero = 2( +2";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeStringsValidasComUmEspacoAntesDasStringsRetornoUmaMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("\"nome:José\"");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : String\n" +
                "var nome : String\n" +
                "nome =   \"nome:\" <>     \"José\"\n" +
                "z=nome";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: \"nome:José\" \n"));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeNumerosInvalidaEntaoRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nvar x : String\nx = 10 <> 20";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("Seu código está correto.")));
    }

    @Test
    public void dadoQueReceboUmaConcatenacaoDeStringsUtilizandoSimboloMaiorEMenorEMaisValidasRetornoUmaMensagemDeErroDiferenteDeVazio() throws Exception {
        String codigo = "varres z : String\n\"nome:\" <> \"Bernardo\" + \"José\"";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaOperacaoAritmeticaInvalidaRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nnumero = 2 + 2)";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaOperacaoAritmeticaInvalidaUtilizandoDoisOperadoresJuntosRetornoUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nnumero = 2 * + 2";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is(not("")));
    }

    @Test
    public void dadoQueReceboUmaExpressaoSemEspacoRetornaMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("4");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : Inteiro\nvar numero : Inteiro\n" +
                "numero=2+2\nz=numero";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("Seu código está correto.\nResultado: 4 \n"));
    }

    @Test
    public void dadoQueNaoDeclareiAVariaveLEAtribuiOValorUmEntaoTereiUmaMensagemDeErro() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : String\nx = 1";

        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("A variável x não foi declarada."));
    }

    @Test
    public void dadoQueAtribuiUmNumeroAUmaVariavelDoTipoStringEntaoTereiUmaMensagemDeErro() throws Exception {
        String codigo = "varres z : String\nvar x : String\n" +
                "x = 1";

        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);
        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro, is("A variável x só aceita atribuição de valores do tipo String."));
    }

    @Test
    public void dadoQueAtribuoUmaOperacaoAritmeticaDeUmIdvComUmNumeroAUmIdvEntãoReceboUmaMensagemDeSucesso() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("1");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);

        String codigo = "varres z : Inteiro\nvar x : Inteiro\nvar y : Inteiro\nz = y + 1";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro,is("Seu código está correto.\nResultado: 1 \n"));
    }

    @Test
    public void dadoQueFizUmaAtribuicaoUtilizandoDoisPontosNoLugarDeIgualRetornoMensagemDeErro() throws Exception {
        resultadosDoUsuario = new ArrayList<String>();
        resultadosDoUsuario.add("Bernardo");
        when(exercicio.getResultadosDoProfessorComoLista()).thenReturn(resultadosDoUsuario);
        String codigo = "varres z : String\nnome : \"Bernardo\" ";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro,is("Sinal de igual esperado para atribuição. \n"));
    }

    @Test
    public void quandoEnvioUmaSolucaoEsquecendoDeFecharAspasAtribuicaoEInvalida() throws Exception {
        String codigo = "varres z : String\nvar nome : String\nnome = \"Bernardo ";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro,is("Variável, valor numérico ou uma string são esperados. \n"));
    }

    @Test
    public void quandoEuEnvioExercicioVazioOValidadorDeVariavelDeResultadoRetornaMensagemDeSolucaoVazia() throws Exception {
        String codigo = "";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo, tabelaDeSimbolos, exercicio);

        String mensagemDeErro = gerenciadorDeFeedback.pegaFeedback();

        assertThat(mensagemDeErro,is("O terminal está vazio.\n"));
    }
}