package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SolucaoDoExercicio extends Model{

    public SolucaoDoExercicio(String cod){
        codigo = cod;
    }

    @Id
    @Constraints.Required
    public int id;

    @Constraints.Required
    public String codigo = "oi";

    public String getCodigo() {

        return codigo;
    }

    public static Finder<Integer,SolucaoDoExercicio> find = new Finder(
            Integer.class, SolucaoDoExercicio.class
    );

    public static List<SolucaoDoExercicio> all() {

        return find.all();
    }

    public static String create(SolucaoDoExercicio solucaoDoExercicio) {
        solucaoDoExercicio.save();
        if(solucaoDoExercicio.find.byId(solucaoDoExercicio.id) != null){
            return "inserido com sucesso!";
        }
        else
            return "Erro: Exercicio não inserido";
    }
}
