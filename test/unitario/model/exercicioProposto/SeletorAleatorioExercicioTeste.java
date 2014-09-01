package unitario.model.exercicioProposto;

import models.Usuario;
import models.exercicioProposto.Exercicio;
import models.exercicioProposto.SeletorAleatorioExercicio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeletorAleatorioExercicioTeste {
    @Mock Usuario usuario;
    private SeletorAleatorioExercicio seletorAleatorioExercicio;

    @Before
    public void setUp() throws Exception {
        List <Exercicio> exerciciosQueEstariamNoBanco = new ArrayList<Exercicio>();
        Exercicio exercicio1 = new Exercicio("exercicio 1", null, null, null);
        Exercicio exercicio2 = new Exercicio("exercicio 2", null, null, null);
        Exercicio exercicio3 = new Exercicio("exercicio 3", null, null, null);
        Exercicio exercicio4 = new Exercicio("exercicio 4", null, null, null);

        exerciciosQueEstariamNoBanco.add(exercicio1);
        exerciciosQueEstariamNoBanco.add(exercicio2);
        exerciciosQueEstariamNoBanco.add(exercicio3);
        exerciciosQueEstariamNoBanco.add(exercicio4);

        when(usuario.todosNaoResolvidos()).thenReturn(exerciciosQueEstariamNoBanco);

        seletorAleatorioExercicio = new SeletorAleatorioExercicio(usuario);
    }

    @Test
    public void selecionaAleatoriamenteUmExercicio() throws Exception {
        ArrayList<Exercicio> lista1 = new ArrayList<Exercicio>();
        ArrayList<Exercicio> lista2 = new ArrayList<Exercicio>();

        for(int i = 0; i<20; i++) {
            lista1.add(seletorAleatorioExercicio.buscaExercicioNaoResolvido());
            lista2.add(seletorAleatorioExercicio.buscaExercicioNaoResolvido());
        }

        assertThat(lista1.toArray(),not(equalTo(lista2.toArray())));
    }

    @Test
    public void selecionaUmExercicioAleatorioNaoResolvidoNoBanco() throws Exception {
        seletorAleatorioExercicio.buscaExercicioNaoResolvido();

        verify(usuario).todosNaoResolvidos();
    }

    @Test
    public void dadoQueTenhoUmaListaDeExercicioVaziaQuandoBuscoExercicioEntaoTenhoUmRetornoNull() throws Exception {
        List <Exercicio> exerciciosResolvidos = new ArrayList<Exercicio>();

        when(usuario.todosNaoResolvidos()).thenReturn(exerciciosResolvidos);

        assertNull(seletorAleatorioExercicio.buscaExercicioNaoResolvido());
    }
}