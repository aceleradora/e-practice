package models.exercicioProposto;

import com.avaje.ebean.Ebean;
import models.SolucaoDoExercicio;
import play.db.ebean.Model;

import java.util.List;

public class SeletorAleatorioExercicio {

    public SeletorAleatorioExercicio() {}

    public static Model.Finder<Integer,Exercicio> find = new Model.Finder(
            Integer.class, Exercicio.class
    );

    public static List<Exercicio> listaDeExercicio() {
        return find.all();
    }

    public String buscaDeExercicioAleatorio(){
        List<Exercicio> listaDeExercicio = listaDeExercicio();
        Exercicio exercicioAleatorio;

        for(int i = 0; i < listaDeExercicio.size(); i++){
            int numeroAleatorio = (int) (Math.random() * listaDeExercicio.size());
            if(listaDeExercicio.get(numeroAleatorio).resolvido == false){
                listaDeExercicio.get(numeroAleatorio).resolvido = true;
                exercicioAleatorio = listaDeExercicio.get(numeroAleatorio);
                return exercicioAleatorio.enunciado;
            }
        }
        return "Não existem exercicios";
    }

    public SolucaoDoExercicio umaPossivelSolucao(){
        SolucaoDoExercicio solucao = new SolucaoDoExercicio("Está é a possivel Solução");
        return solucao;
    }

    public void createExercicioPadrao(){
        Exercicio exercicio1 = new Exercicio();
        exercicio1.enunciado = "1) - Exercicio 1";
        exercicio1.possivelSolucao = umaPossivelSolucao();
        exercicio1.save();

        Exercicio exercicio2 = new Exercicio();
        exercicio2.enunciado = "2) - Exercicio 2";
        exercicio2.possivelSolucao = umaPossivelSolucao();
        exercicio2.save();

        Exercicio exercicio3 = new Exercicio();
        exercicio3.enunciado = "3) - Exercicio 3";
        exercicio3.possivelSolucao = umaPossivelSolucao();
        exercicio3.save();
    }


}