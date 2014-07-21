package unitario.model.analizadorSemantico;

import models.TabelaDeSimbolos;
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
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos,tokens);
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
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos,tokens);
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
        ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(tabelaDeSimbolos,tokens);
        boolean validacao = validadorDeAtribuicao.validaExpressao();
        assertThat(validacao,is(false));
    }
}
