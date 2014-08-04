package integracao.models;

import models.GerenciadorDeFeedback;
import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.*;
import models.analisadorSemantico.GerenciadorBuilder;
import models.analisadorSemantico.ValidadorDeAtribuicao;
import models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel;
import models.analisadorSemantico.ValidadorDeOperacoesAritmeticas;
import models.analisadorSintatico.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TesteFeedbackRestricaoSemantica {

    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private GerenciadorDeFeedback gerenciadorDeFeedback;
    private TabelaDeSimbolos tabelaDeSimbolos;

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
    }

    @Test
    public void dadoQueDeclaroAMesmaVariavelDuasVezesDeveRetornarMensagemDeErro() throws Exception {
        String codigo = "var x : String \n";
        codigo += "var x : String";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("Seu código está sintaticamente correto.\nA variável x ja foi declarada."));
    }

    @Test
    public void dadoQueDeclaroAsVariaveisCorretamenteDeveRetornarFeedbackPositivo() throws Exception {
        String codigo = "var x : Inteiro\n";
        codigo += "x = 1";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigo);
        String mensagem = gerenciadorDeFeedback.pegaFeedback();

        assertNotNull(mensagem);
        assertThat(mensagem, is("Seu código está sintaticamente correto.\nSeu código está semanticamente correto.\n"));


    }
}
