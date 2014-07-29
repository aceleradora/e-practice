package models.exercicioProposto;

import models.SolucaoDoExercicio;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    public Exercicio() {}

    public Exercicio(int id, String enunciado, SolucaoDoExercicio possivelSolucao, boolean resolvido) {
        this.id = id;
        this.enunciado = enunciado;
        this.possivelSolucao = possivelSolucao;
        this.resolvido = resolvido;
    }
}
