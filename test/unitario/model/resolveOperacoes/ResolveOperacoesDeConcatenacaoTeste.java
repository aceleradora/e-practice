package unitario.model.resolveOperacoes;

import models.TabelaDeSimbolos;
import models.resolveOperacoes.ResolveOperacoesDeConcatenacao;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResolveOperacoesDeConcatenacaoTeste {
    TabelaDeSimbolos tabelaDeSimbolos;
    ResolveOperacoesDeConcatenacao resolveOperacoesDeConcatenacao;

    @Before
    public void setUp() throws Exception {
        tabelaDeSimbolos = new TabelaDeSimbolos();
        resolveOperacoesDeConcatenacao = new ResolveOperacoesDeConcatenacao(tabelaDeSimbolos);
    }

    @Test
    public void dadoUmaOperacaoDeConcatenacaoComDuasStringsRetornaUmaString() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("\"casa \"");
        tokens.add("<>");
        tokens.add("\"branca\"");

        String result = resolveOperacoesDeConcatenacao.concatenaStrings(tokens);

        assertThat(result, is("\"casa branca\""));
    }

    @Test
    public void dadoUmaOperacaoDeConcatenacaoComTresStringsRetornaUmaString() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("\"casa \"");
        tokens.add("<>");
        tokens.add("\"branca \"");
        tokens.add("<>");
        tokens.add("\"linda\"");

        String result = resolveOperacoesDeConcatenacao.concatenaStrings(tokens);

        assertThat(result, is("\"casa branca linda\""));
    }

    @Test
    public void dadoUmaOperacaoDeConcatenacaoComQuatroStringsRetornaUmaStringSemEspaco() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("\"casa\"");
        tokens.add("<>");
        tokens.add("\"branca\"");
        tokens.add("<>");
        tokens.add("\"linda\"");
        tokens.add("<>");
        tokens.add("\"linda\"");

        String result = resolveOperacoesDeConcatenacao.concatenaStrings(tokens);

        assertThat(result, is("\"casabrancalindalinda\""));
    }

    @Test
    public void dadoUmaOperacaoDeConcatenacaoDeUmaStringEUmaVariavelStringsRetornaUmaString() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tabelaDeSimbolos.adicionaSimbolo("x", "String");
        tabelaDeSimbolos.atualizaValor("x", "\"branca\"");

        tokens.add("\"casa \"");
        tokens.add("<>");
        tokens.add(tabelaDeSimbolos.getValor("x"));

        String result = resolveOperacoesDeConcatenacao.concatenaStrings(tokens);

        assertThat(result, is("\"casa branca\""));
    }
}
