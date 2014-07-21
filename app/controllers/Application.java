package controllers;

import play.data.Form;
import play.mvc.*;
import models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    private static SolucaoDoExercicio solucaoDoExercicio;
    static FeedBacker feedBacker = new FeedBacker();

    public Application(SolucaoDoExercicio solucaoDoExercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
    }

    public static Result index() {
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        List<SolucaoDoExercicio> all = solucaoDoExercicio.all();

        return ok(views.html.index.render(all, solucaoDoExercicioForm));
    }

    public static Result novaSolucao() {
        Form<SolucaoDoExercicio> formPreenchido = solucaoDoExercicioForm.bindFromRequest();

        if(formPreenchido.hasErrors()){
            flash("status", "Status: erro!");
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido));
        } else{
            try{
                SolucaoDoExercicio.create(formPreenchido.get());
                flash("status", "Status: sua solução foi salva com sucesso!");
            } catch (Exception e){
                flash("status", "Status: sua solução não foi salva");
            }
            return redirect(routes.Application.solucoes());
        }
    }

    public static Result deletaSolucao(int id){
        SolucaoDoExercicio.delete(id);
        flash("status", "Status: deletado!");

        return redirect(routes.Application.solucoes());
    }

}
