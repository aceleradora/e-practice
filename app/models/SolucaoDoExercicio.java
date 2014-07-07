package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import scala.languageFeature;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SolucaoDoExercicio extends Model{

    @Id
    @Constraints.Required
    public int id;

    @Constraints.Required (message = "A solução do exercício está vazia. Preencha corretamente.")
    public String solucaoDoUsuario;


    public SolucaoDoExercicio(String solucaoDoUsuario){
        this.solucaoDoUsuario = solucaoDoUsuario;
    }

    public static Finder<Integer,SolucaoDoExercicio> find = new Finder(
            Integer.class, SolucaoDoExercicio.class
    );

    public static List<SolucaoDoExercicio> all() {
        return find.all();
    }

    public void create() {
        this.save();
    }

    public static void create(SolucaoDoExercicio solucaoDoExercicio) throws Exception{
        solucaoDoExercicio.save();
    }

    public static void delete(int id){
        find.ref(id).delete();
    }
}
