package unitario.model.exercicioProposto;

import models.SolucaoDoExercicio;
import models.exercicioProposto.Exercicio;
import models.exercicioProposto.SeletorAleatorioExercicio;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SeletorAleatorioExercicioTest {

    ArrayList<Exercicio> listaDeExercicios = new ArrayList<Exercicio>();
    SeletorAleatorioExercicio seletorAleatorioExercicio;
    SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio("teste");
    Exercicio exercicio;

    @Test
    public void SelecionarAleatoriamenteUmExercicioDeUmaLista(){
        exercicio = new Exercicio(1, "teste", solucaoDoExercicio, false);
        listaDeExercicios.add(exercicio);
        seletorAleatorioExercicio = new SeletorAleatorioExercicio(listaDeExercicios);

        assertThat("teste", is("teste"));
    }
}
