package controllers;

import com.avaje.ebean.Ebean;
import models.SolucaoDoExercicio;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Exercicio extends Controller {

    public static Result criaExercicios() {
        List<Usuario> todosUsuarios = Ebean.find(Usuario.class).findList();
        List<SolucaoDoExercicio> todasSolucoes = Ebean.find(SolucaoDoExercicio.class).findList();
        List<models.exercicioProposto.Exercicio> todosExercicios = Ebean.find(models.exercicioProposto.Exercicio.class).findList();

        for(Usuario usuario: todosUsuarios){
            Ebean.deleteManyToManyAssociations(usuario, "exerciciosResolvidos");
        }

        Ebean.delete(todasSolucoes);
        Ebean.delete(todosExercicios);
        Ebean.delete(todosUsuarios);


        models.exercicioProposto.Exercicio exercicio1 = new models.exercicioProposto.Exercicio();
        exercicio1.enunciado = "Dados 3 valores inteiros 5, 12, e 20, calcule:\n" +
                "a) A soma dos três valores;\n" +
                "b) A multiplicação dos 3 valores;\n" +
                "c) A média aritmética dos três valores.";
        exercicio1.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio1.save();

        models.exercicioProposto.Exercicio exercicio2 = new models.exercicioProposto.Exercicio();
        exercicio2.enunciado = "As colunas que sustentam a cobertura no Estádio Beira-Rio são de formato " +
                "cilindrico, sabendo que as colunas tem 40m de altura e 8 metros de largura, calcule " +
                "o volume de cimento usado para construir estas colunas.";
        exercicio2.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio2.save();

        models.exercicioProposto.Exercicio exercicio3 = new models.exercicioProposto.Exercicio();
        exercicio3.enunciado = "Compute a string resultante de se justapor as palavras \"casa\" com a palavra " +
                "\"mento\" e a palavra \"rápido\". ";
        exercicio3.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio3.save();

        return redirect(routes.Application.selecionaProximoExercicio());
    }

}