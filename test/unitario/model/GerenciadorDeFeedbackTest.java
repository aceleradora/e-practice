package unitario.model;


import models.GerenciadorDeFeedback;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSintatico.GerenciadorDeValidacao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GerenciadorDeFeedbackTest {

    @Mock private GerenciadorDeValidacao gerenciadorDeValidacao;
    @Mock private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private GerenciadorDeFeedback gerenciadorDeFeedback;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> linhaUnica = new ArrayList<String>();
        linhaUnica.add("var x : String");

        ArrayList<String> linhaTripla = new ArrayList<String>();
        linhaTripla.add("var x : String");
        linhaTripla.add("x = 1");
        linhaTripla.add("x = x + 1");


        when(quebradorDeCodigo.quebra("var x : String")).thenReturn(linhaUnica);
        when(quebradorDeCodigo.quebra("var x : String \n x = 1 \n x = x + 1")).thenReturn(linhaTripla);
    }

    @Test
    public void dadoQueReceboUmCodigoComTresExpressoesEntaoOMetodoQuebraSeraChamado() throws Exception {
        String codigo = "var x : String \n x = 1 \n x = x + 1";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        verify(quebradorDeCodigo, times(1)).quebra(codigo);
    }

    @Test
    public void dadoQueFoiRecebidoUmCodigoEntaoDeveSerChamadoOMetodoInterpretaDaClasseGerenciadorDeValidacao() throws Exception {
        String codigo = "var x : String";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        gerenciadorDeFeedback.pegaFeedback();

        verify(gerenciadorDeValidacao).interpreta(codigo);
    }

    @Test
    public void dadoQueFoiRebebidoUmCodigoComTresExpressoesEntaoChamaTresVezesInterpreta() throws Exception {
        String codigo = "var x : String \n x = 1 \n x = x + 1";
        gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorDeValidacao, quebradorDeCodigo);

        gerenciadorDeFeedback.pegaFeedback();

        verify(gerenciadorDeValidacao, times(3)).interpreta(anyString());
    }

}
