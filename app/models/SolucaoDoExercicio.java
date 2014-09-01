package models;

import models.exercicioProposto.Exercicio;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SolucaoDoExercicio extends Model{

    @Id
    @Constraints.Required
    public int id;

    @Constraints.Required (message = "A solução do exercício está vazia. Preencha corretamente.")
    @Column(columnDefinition = "TEXT")
    public String solucaoDoUsuario;

    @Column(columnDefinition = "Integer")
    public int idDoUsuario;

    @ManyToOne
    Exercicio exercicio;

    public SolucaoDoExercicio(String solucaoDoUsuario){
        this.solucaoDoUsuario = solucaoDoUsuario;
    }

    public static Finder<Integer,SolucaoDoExercicio> find = new Finder(
            Integer.class, SolucaoDoExercicio.class
    );

    public static List<SolucaoDoExercicio> all() {
        return find.all();
    }

    public static void create(SolucaoDoExercicio solucaoDoExercicio) throws Exception{
        solucaoDoExercicio.save();
    }

    public static void delete(int id){
        find.ref(id).delete();
    }

    public String getSolucaoDoUsuario() {
        return solucaoDoUsuario;
    }

    public Exercicio getExercicio(){
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio){
        this.exercicio = exercicio;
    }

    public void criaSolucao(Exercicio exercicio, int id) {
        setExercicio(exercicio);
        idDoUsuario = id;
        save();
    }
}
