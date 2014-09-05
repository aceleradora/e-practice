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
        exercicio1.enunciado = getEnunciadoExercicioUm();
        exercicio1.solucaoDoProfessor = getSolucaoExercicioUm();
        exercicio1.resultadoDoProfessor = "37\n1200\n12";
        exercicio1.save();

        models.exercicioProposto.Exercicio exercicio2 = new models.exercicioProposto.Exercicio();
        exercicio2.enunciado = getEnunciadoExercicioDois();
        exercicio2.solucaoDoProfessor = getSolucaoExercicioDois();
        exercicio2.resultadoDoProfessor = "1920";
        exercicio2.save();

        models.exercicioProposto.Exercicio exercicio3 = new models.exercicioProposto.Exercicio();
        exercicio3.enunciado = getEnunciadoExercicioTres();
        exercicio3.solucaoDoProfessor = getSolucaoExercicioTres();
        exercicio3.resultadoDoProfessor = "\"casamento rápido\"";
        exercicio3.save();

        return redirect(routes.Application.index());
    }

    private static String getEnunciadoExercicioUm() {
        return "Dados 3 valores inteiros 5, 12, e 20, calcule:\n" +
                "a) A soma dos três valores;\n" +
                "b) A multiplicação dos 3 valores;\n" +
                "c) A média aritmética dos três valores.";
    }

    private static String getSolucaoExercicioUm() {
        return "var valorUm : Inteiro\n" +
               "var valorDois : Inteiro\n" +
               "var valorTres : Inteiro\n" +
               "varres resultadoA : Inteiro\n" +
               "varres resultadoB : Inteiro\n" +
               "varres resultadoC : Inteiro\n\n" +

                "valorUm = 5 \n" +
                "valorDois = 12\n" +
                "valorTres = 20\n\n" +

                "resultadoA = valorUm + valorDois + valorTres\n" +
                "resultadoB = valorUm * valorDois * valorTres\n" +
                "resultadoC = (valorUm + valorDois + valorTres) / 3\n";
    }

    private static String getEnunciadoExercicioDois() {
        return "As colunas que sustentam a cobertura no Estádio Beira-Rio são de formato " +
                "cilindrico, sabendo que as colunas tem 40m de altura e 8 metros de largura, calcule " +
                "o volume de cimento usado para construir estas colunas.";
    }

    private static String getSolucaoExercicioDois() {
        return "var calculo : Inteiro\n" +
               "varres resultado : Inteiro\n\n" +

               "calculo = 3 * (4 * 4) * 40\n\n" +

               "resultado = calculo\n";
    }

    private static String getEnunciadoExercicioTres() {
        return "Compute a string resultante de se justapor as palavras \"casa\" com a palavra " +
                "\"mento\" e a palavra \" rápido\". ";
    }

    private static String getSolucaoExercicioTres() {
        return "var palavraUm : String\n" +
               "var palavraDois : String\n" +
               "var palavraTres : String\n" +
               "varres resultado : String\n\n" +

               "palavraUm = \"casa\"\n" +
               "palavraDois = \"mento\"\n" +
               "palavraTres = \" rápido\"\n" +
               "resultado = palavraUm <> palavraDois <> palavraTres";
    }
}