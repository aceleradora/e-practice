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
    public String solucaoDoProfessor;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String resultadoDoProfessor;

    @OneToMany
    List<SolucaoDoExercicio> solucoesDoUsuario;

    @ManyToMany
    List<Usuario> usuarios;

    public Exercicio() {}

    public Exercicio(String enunciado, String solucaoDoProfessor) {
        this.enunciado = enunciado;
        this.solucaoDoProfessor = solucaoDoProfessor;
    }

    private static Model.Finder<Integer,Exercicio> find = new Model.Finder(
            Integer.class, Exercicio.class
    );

    public List<Exercicio> todos() {
        return find.all();
    }

    public List<String> getResultadosDoProfessorComoLista() {
        ArrayList<String> resultados = new ArrayList<String>();
        String[] resultadosDivididos = resultadoDoProfessor.split("\n");
        for (String resultado : resultadosDivididos) {
            resultados.add(resultado);
        }
        return resultados;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Exercicio exercicio = (Exercicio) object;

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