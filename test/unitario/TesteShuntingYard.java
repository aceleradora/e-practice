package unitario;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorSemantico.ShuntingYard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by aluno2 on 24/07/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class TesteShuntingYard {
    @Mock TabelaDeSimbolos tabelaDeSimbolos;
    @Mock IdentificadorDeToken identificadorDeToken;
    ArrayList<String> tokens;
    ShuntingYard shuntingYard;
    @Before
    public void setUp() throws Exception {
        tokens = new ArrayList<String>();
        tokens.add("A");
        tokens.add("+");
        tokens.add("B");
        tokens.add("*");
        tokens.add("C");
        tokens.add("-");
        tokens.add("D");
        when(tabelaDeSimbolos.getTipoSimbolo("A")).thenReturn("Inteiro");
        when(tabelaDeSimbolos.getTipoSimbolo("B")).thenReturn("Inteiro");
        when(tabelaDeSimbolos.getTipoSimbolo("C")).thenReturn("Inteiro");
        when(tabelaDeSimbolos.getTipoSimbolo("D")).thenReturn("Inteiro");
        when(tabelaDeSimbolos.simboloExiste("A")).thenReturn(true);
        when(tabelaDeSimbolos.simboloExiste("B")).thenReturn(true);
        when(tabelaDeSimbolos.simboloExiste("C")).thenReturn(true);
        when(tabelaDeSimbolos.simboloExiste("D")).thenReturn(true);
        shuntingYard = new ShuntingYard(identificadorDeToken,tabelaDeSimbolos);

    }
    
    @Test
    public void seTokenEhNumeroOuVariavelInteiroEntaoAdicionaNaFila() throws Exception {
        boolean resultado = shuntingYard.adicionaTokenSeForInteiro(tokens.get(0));
        assertThat(resultado,is(true));
    }

    @Test
    public void adicionaTokensDeNumeroOuVariavelAteAcharUmOperador() throws Exception {
        shuntingYard.adicionaAteAcharOperador(tokens);
        assertThat(shuntingYard.fila.tamanhoDaFila(), is(1));
    }
}
