package models;

import models.exercicioProposto.Exercicio;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario extends Model {

    @Id
    public int id;

    @ManyToMany(mappedBy = "usuarios")
    public List<Exercicio> exerciciosResolvidos = new ArrayList<Exercicio>();


    public List<Exercicio> todosNaoResolvidos() {
        Exercicio exercicio = new Exercicio();
        List<Exercicio> todos = exercicio.todos();
        todos.removeAll(exerciciosResolvidos);
        return todos;
    }

    public void seNaoHouverExercicioResolvidoAdicionaExercicio(Exercicio exercicio) {
        if(!exerciciosResolvidos.contains(exercicio)) {
            exerciciosResolvidos.add(exercicio);
            save();
        }
    }
}