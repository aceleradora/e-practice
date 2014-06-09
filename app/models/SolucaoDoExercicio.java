package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SolucaoDoExercicio extends Model{

    @Id
    @Constraints.Required
    public Long id;

    @Constraints.Required
    public String codigo = "oi";


    public String getCodigo() {
        return codigo;
    }

    public static Finder<Long,SolucaoDoExercicio> find = new Finder(
            Long.class, SolucaoDoExercicio.class
    );

    public static List<SolucaoDoExercicio> all() {
        return find.all();
    }

    public static void create(SolucaoDoExercicio solucaoDoExercicio) {
        solucaoDoExercicio.save();
    }
}
