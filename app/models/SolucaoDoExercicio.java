package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SolucaoDoExercicio extends Model{

    public SolucaoDoExercicio(String codigo){
        this.codigo = codigo;
    }

    @Id
    @Constraints.Required
    public int id;

    @Constraints.Required
    public String codigo;

    public String label;

    public static Finder<Integer,SolucaoDoExercicio> find = new Finder(
            Integer.class, SolucaoDoExercicio.class
    );

    public static List<SolucaoDoExercicio> all() {

        return find.all();
    }

    public static void create(SolucaoDoExercicio solucaoDoExercicio) {

    }
}
