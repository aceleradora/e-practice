package unitario.model.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorSemantico.ValidadorDeAtribuicao;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ValidadorDeAtribuicaoTeste {

    TabelaDeSimbolos tabelaDeSimbolos;
    ValidadorDeAtribuicao validadorDeAtribuicao;
    ArrayList<String> tokens;

    @Before
    public void setUp() throws Exception {
        this.tabelaDeSimbolos = new TabelaDeSimbolos();
        this.validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        this.tokens = new ArrayList<String>();
    }

    @Test
    public void quandoEuAtribuoValorAUmaVariavelNaoDeclaradaEuReceboUmaMensagemDeErroEspecifica() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("\"1\"");
        validadorDeAtribuicao.valida(tokens);
        String validacao = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(validacao,is("A variável "+tokens.get(0)+" não foi declarada."));
    }

    @Test
    public void quandoEuAtribuoValorIncompativelAUmaVariavelEuReceboUmaMensagemDeErroEspecifica() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("1");
        tabelaDeSimbolos.adicionaSimbolo("x", "String");
        validadorDeAtribuicao.valida(tokens);
        String validacao = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(validacao,is("A variável "+tokens.get(0)+" só aceita atribuição de valores do tipo "+tabelaDeSimbolos.getTipoSimbolo(tokens.get(0))+"."));
    }

    @Test
    public void quandoReceboUmArrayDeTokensDeAtribuicaoPossoValidarEsseArray() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("1");
        tabelaDeSimbolos.adicionaSimbolo("x", "String");
        boolean resultado = validadorDeAtribuicao.valida(tokens);

        assertThat(resultado,is(false));
    }

    @Test
    public void quandoEuAtribuoUmIdvStringAUmaIdvInteiroReceboUmaMensagemDeErroEspecifica() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("y");
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");
        tabelaDeSimbolos.adicionaSimbolo("y", "String");
        validadorDeAtribuicao.valida(tokens);
        String validacao = validadorDeAtribuicao.retornaMensagemErro();

        assertThat(validacao, is ("A variável "+tokens.get(0)+" só aceita atribuição de valores do tipo "+tabelaDeSimbolos.getTipoSimbolo(tokens.get(0))+"."));

    }

    @Test
    public void quandoEuAtribuoUmIdvdStringAUmIdvStringRetornaTrue() throws Exception {
        tokens.add("x");
        tokens.add("=");
        tokens.add("c");
        tabelaDeSimbolos.adicionaSimbolo("x", "String");
        tabelaDeSimbolos.adicionaSimbolo("c", "String");
        boolean resultado = validadorDeAtribuicao.valida(tokens);

        assertThat(resultado,is(true));

    }
}
