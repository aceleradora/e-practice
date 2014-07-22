package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorSemantico.ValidadorDeAtribuicao;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by alunos4 on 21/07/14.
 */
public class TesteValidadorDeAtribuicao {


    @Test
    public void seEuAtribuoUmValorAUmaVariavelNaoDeclaradaRetornaFalse() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("y");
        tokens.add("=");
        tokens.add("\"1\"");
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x","String");
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao.valida(tokens);
        boolean validacao = validadorDeAtribuicao.validaVariavel();
        assertThat(validacao,is(false));

    }

    @Test
    public void quandoEuAtribuoUmValorDeTipoInteiroParaUmaVariavelDoTipoStringRetornaFalse() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("x");
        tokens.add("=");
        tokens.add("1");
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x","String");
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao.valida(tokens);
        boolean validacao = validadorDeAtribuicao.validaExpressao();
        assertThat(validacao,is(false));
    }

    @Test
    public void quandoEuAtribuoUmValorDeTipoStringParaUmaVariavelDoTipoInteiroRetornaFalse() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("x");
        tokens.add("=");
        tokens.add("\"1\"");
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x","Inteiro");
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao.valida(tokens);
        boolean validacao = validadorDeAtribuicao.validaExpressao();
        assertThat(validacao,is(false));
    }

    @Test
    public void quandoEuAtribuoValorAUmaVariavelNaoDeclaradaEuReceboUmaMensagemDeErroEspecifica() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("x");
        tokens.add("=");
        tokens.add("\"1\"");
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("y","String");
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao.valida(tokens);
        String validacao = validadorDeAtribuicao.retornaMensagemErro();
        assertThat(validacao,is("A variável "+tokens.get(0)+" não foi declarada."));
    }

    @Test
    public void quandoEuAtribuoValorIncompativelAUmaVariavelEuReceboUmaMensagemDeErroEspecifica() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("x");
        tokens.add("=");
        tokens.add("1");
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x","String");
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao.valida(tokens);
        String validacao = validadorDeAtribuicao.retornaMensagemErro();
        assertThat(validacao,is("A Variavel "+tokens.get(0)+" só aceita atribuição de valores do tipo "+tabelaDeSimbolos.getTipoSimbolo(tokens.get(0))+"."));

    }

    @Test
    public void quandoReceboUmArrayDeTokensDeAtribuicaoPossoValidarEsseArray() throws Exception {
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add("x");
        tokens.add("=");
        tokens.add("1");
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x","String");
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos);
        validadorDeAtribuicao.valida(tokens);
        boolean resultado = validadorDeAtribuicao.valida(tokens);
        assertThat(resultado,is(false));
    }
}
