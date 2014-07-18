package unitario.model;

import models.GerenciadorDeValidacao;
import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import models.analisadorSintatico.ValidadorDeAtribuicao;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class TesteGerenciadorDeValidacao {

    Lexer lexer;
    IdentificadorDeToken identificadorDeToken;
    TabelaDeSimbolos tabelaDeSimbolos;
    @Mock ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    @Mock ValidadorDeAtribuicao validadorDeAtribuicao;
    @Mock GerenciadorDeValidacao gerenciadorDeValidacao;


    @Before
    public void setUp() throws Exception {
        this.lexer = new Lexer();
        this.identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = new TabelaDeSimbolos();

    }

    @Test
    public void quandoRecebeUmaStringComecandoComVarChamaOValidadorDeDeclaracao() throws Exception {
        String codigo = "var abacaxi : String";
        ArrayList<String> tokens = lexer.tokenizar(codigo);
        GerenciadorDeValidacao gerenciadorDeValidacao = new GerenciadorDeValidacao();

        boolean resultado = gerenciadorDeValidacao.verificaSeEhDeclaracao(tokens);

        assertThat(resultado, is(true));

    }

    @Test
    public void quandoRecebeUmaStringComecandoComIDVChamaOValidadorDeAtribuicao() throws Exception {
        String codigo = "x = 1";
        when(gerenciadorDeValidacao.verificaSeEhAtribuicao(codigo)).thenReturn(true);

        boolean resultado = gerenciadorDeValidacao.verificaSeEhAtribuicao(codigo);

        assertThat(resultado, is(true));

    }

    @Test
    public void quandoRecebeOperacaoAritmeticaChamaOValidadorDeOperacoesAritmeticas() throws Exception {
        String codigo = "1 + 1";
        ArrayList<String> tokens = lexer.tokenizar(codigo);
        when(gerenciadorDeValidacao.verificaSeEhOperacaoAritmetica(tokens)).thenReturn(true);

        boolean resultado = gerenciadorDeValidacao.verificaSeEhOperacaoAritmetica(tokens);

        assertThat(resultado, is(true));

    }

    @Test
    public void quandoRecebeAtribuicaoDeStringChamaOValidacaoDeAtribuicaoDeStrings() throws Exception {
        String codigo = "x = casa <> grande";

        when(gerenciadorDeValidacao.verificaSeEhAtribuicaoDeString(codigo)).thenReturn(true);

        boolean resultado = gerenciadorDeValidacao.verificaSeEhAtribuicaoDeString(codigo);

        assertThat(resultado, is(true));

    }


}