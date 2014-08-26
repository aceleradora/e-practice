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
    public SolucaoDoExercicio possivelSolucao;

    @Column(columnDefinition = "boolean DEFAULT false")
    public boolean resolvido = false;

    @OneToMany
    List<SolucaoDoExercicio> solucoesDoUsuario;

    @ManyToMany
    List<Usuario> usuarios;

    public Exercicio() {}

    public Exercicio(String enunciado, SolucaoDoExercicio possivelSolucao, boolean resolvido) {

        this.enunciado = enunciado;
        this.possivelSolucao = possivelSolucao;
        this.resolvido = resolvido;
    }

    private static Model.Finder<Integer,Exercicio> find = new Model.Finder(
            Integer.class, Exercicio.class
    );

    public List<Exercicio> todosNaoResolvidos() {
        return find.where().eq("resolvido", false).findList();
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

}
