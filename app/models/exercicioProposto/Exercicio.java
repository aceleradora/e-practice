package models.exercicioProposto;

import com.avaje.ebean.Ebean;
import models.SolucaoDoExercicio;
import play.db.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.List;

@Entity
public class Exercicio extends Model{

    @Id
    public int id;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String enunciado;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public SolucaoDoExercicio possivelSolucao;

    @Column(columnDefinition = "boolean DEFAULT false")
    public boolean isResolvido = false;

    public Exercicio() {}

    public Exercicio(int id, String enunciado, SolucaoDoExercicio possivelSolucao, boolean isResolvido) {
        this.id = id;
        this.enunciado = enunciado;
        this.possivelSolucao = possivelSolucao;
        this.isResolvido = isResolvido;
    }

    public SolucaoDoExercicio umaPossivelSolucao(){
        SolucaoDoExercicio solucao = new SolucaoDoExercicio("Está é a possivel Solução");
        return solucao;
    }

    public static Finder<Integer,Exercicio> find = new Finder(
            Integer.class, Exercicio.class
    );

    public static List<Exercicio> all() {
        return find.all();
    }

    public String pegaExercicioAleatorio(){
        int numeroAleatorio = (int) (Math.random() * 3);

        List<Exercicio> all = all();
        Exercicio exercicioAleatorio = all.get(numeroAleatorio);
        return exercicioAleatorio.enunciado;
    }

    public void createExercicioPadrao(){
        Ebean.delete(all());

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
