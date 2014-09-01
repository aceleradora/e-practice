package models.exercicioProposto;

import models.SolucaoDoExercicio;
import models.Usuario;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
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
    public SolucaoDoExercicio solucaoDoProfessor;

    @OneToMany
    List<SolucaoDoExercicio> solucoesDoUsuario;

    @ManyToMany
    List<Usuario> usuarios;
    private ArrayList<String> resultadoDoProfessor;

    public Exercicio() {}

    public Exercicio(String enunciado, SolucaoDoExercicio solucaoDoProfessor) {
        this.enunciado = enunciado;
        this.solucaoDoProfessor = solucaoDoProfessor;
    }

    private static Model.Finder<Integer,Exercicio> find = new Model.Finder(
            Integer.class, Exercicio.class
    );

    public List<Exercicio> todos() {
        return find.all();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercicio exercicio = (Exercicio) o;

        if (!enunciado.equals(exercicio.enunciado)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + enunciado.hashCode();
        return result;
    }

    public ArrayList<String> getResultadoDoProfessor() {
        return resultadoDoProfessor;
    }
}