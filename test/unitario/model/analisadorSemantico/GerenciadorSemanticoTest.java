package unitario.model.analisadorSemantico;

import models.analisadorLexico.Lexer;
import models.analisadorSemantico.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorSemanticoTest {

    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    @Mock ValidadorDeConcatenacao validadorDeConcatenacao;

    private String declaracaoString;
    private String atribuicaoDeString;
    private ArrayList<String> listaDeTokensDeDeclaracaoDeString;
    private ArrayList<String> listaDeTokensDeAtribuicaoDeString;
    private ArrayList<String> listaDeTokensDeConcatenacao;
    private GerenciadorSemantico gerenciadorSemantico;
    private String sentencaDeConcatenacao;

    @Before
    public void setUp() throws Exception {
        GerenciadorBuilder gerenciadorBuilder = new GerenciadorBuilder();

        gerenciadorSemantico = gerenciadorBuilder.com(validadorDeAtribuicao)
                .com(validadorDeDeclaracao)
                .com(validadorDeConcatenacao)
                .geraGerenciador();

        declaracaoString = "var nome : String";
        atribuicaoDeString = "nome = \"alejandro\"";
        sentencaDeConcatenacao = "nome = \"João\" <> \"Henrique\"";
        listaDeTokensDeDeclaracaoDeString = new ArrayList<String>();
        listaDeTokensDeAtribuicaoDeString = new ArrayList<String>();
        listaDeTokensDeConcatenacao = new ArrayList<String>();

        criaListaDeTokensDeDeclaracaoDeString();
        criaLIstaDeTokensDeAtribuicaoDeString();
        criaListaDeTokensDeConcatenacao();
    }

    private void criaListaDeTokensDeConcatenacao() {
        listaDeTokensDeConcatenacao.add("nome");
        listaDeTokensDeConcatenacao.add("=");
        listaDeTokensDeConcatenacao.add("\"João\"");
        listaDeTokensDeConcatenacao.add("<>");
        listaDeTokensDeConcatenacao.add("\"Henrique\"");
    }

    private void criaListaDeTokensDeDeclaracaoDeString() {
        listaDeTokensDeDeclaracaoDeString.add("var");
        listaDeTokensDeDeclaracaoDeString.add("nome");
        listaDeTokensDeDeclaracaoDeString.add(":");
        listaDeTokensDeDeclaracaoDeString.add("String");
    }

    private void criaLIstaDeTokensDeAtribuicaoDeString() {
        listaDeTokensDeAtribuicaoDeString.add("nome");
        listaDeTokensDeAtribuicaoDeString.add("=");
        listaDeTokensDeAtribuicaoDeString.add("\"alejandro\"");
    }

    @Test
    public void chamaValidadorDeDeclaracaoQuandoPrimeiroTokenForVar() throws Exception {
        gerenciadorSemantico.interpreta(declaracaoString);

        verify(validadorDeDeclaracao).valida(listaDeTokensDeDeclaracaoDeString);
    }

    @Test
    public void chamaValidadorDeAtribuicaoQuandoPrimeiroTokenForIDV() throws Exception {
        gerenciadorSemantico.interpreta(atribuicaoDeString);

        verify(validadorDeAtribuicao).valida(listaDeTokensDeAtribuicaoDeString);
    }

    @Test
    public void chamaValidadorDeConcatenacaoSeHouverOperadorDeConcatenacaoNaSentenca() throws Exception {
        gerenciadorSemantico.interpreta(sentencaDeConcatenacao);

        verify(validadorDeConcatenacao).valida(listaDeTokensDeConcatenacao);
    }

}
