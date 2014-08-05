package unitario.model.exercicioProposto;

import models.exercicioProposto.Exercicio;
import models.exercicioProposto.SeletorAleatorioExercicio;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeletorAleatorioExercicioTest {
    @Mock Exercicio exercicio;
    private SeletorAleatorioExercicio seletorAleatorioExercicio;

    @Before
    public void setUp() throws Exception {
        List <Exercicio> exerciciosQueEstariamNoBanco = new ArrayList<Exercicio>();
        Exercicio exercicio1 = new Exercicio("exercicio 1",null, false);
        Exercicio exercicio2 = new Exercicio("exercicio 2",null, false);
        Exercicio exercicio3 = new Exercicio("exercicio 3",null, false);
        Exercicio exercicio4 = new Exercicio("exercicio 4",null, false);

        exerciciosQueEstariamNoBanco.add(exercicio1);
        exerciciosQueEstariamNoBanco.add(exercicio2);
        exerciciosQueEstariamNoBanco.add(exercicio3);
        exerciciosQueEstariamNoBanco.add(exercicio4);

        when(exercicio.todosNaoResolvidos()).thenReturn(exerciciosQueEstariamNoBanco);

        seletorAleatorioExercicio = new SeletorAleatorioExercicio(exercicio);

    }

    @Test
    public void selecionaAleatoriamenteUmExercicio() throws Exception {
        ArrayList<Exercicio> lista1 = new ArrayList<Exercicio>();
        ArrayList<Exercicio> lista2 = new ArrayList<Exercicio>();

        for(int i = 0; i<20; i++) {
            lista1.add(seletorAleatorioExercicio.buscaExercicio());
            lista2.add(seletorAleatorioExercicio.buscaExercicio());
        }

        assertThat(lista1.toArray(),not(equalTo(lista2.toArray())));
    }

    @Test
    public void selecinaUmExercicioAleatorioNaoResolvidoNoBanco() throws Exception {
        seletorAleatorioExercicio.buscaExercicio();

        verify(exercicio).todosNaoResolvidos();
    }

}
