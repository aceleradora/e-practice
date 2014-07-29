package controllers;

import models.SolucaoDoExercicio;
import models.exercicioProposto.Exercicio;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    private static SolucaoDoExercicio solucaoDoExercicio;
    private static Exercicio exercicio;

    public Application(SolucaoDoExercicio solucaoDoExercicio, Exercicio exercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
        this.exercicio = exercicio;
    }

    public static Result index() {
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        exercicio = new Exercicio();
        session("textoExercicio", exercicio.pegaExercicioAleatorio());

        List<SolucaoDoExercicio> all = solucaoDoExercicio.all();

        return ok(views.html.index.render(all, solucaoDoExercicioForm));
    }

    public static Result novaSolucao() {
        exercicio = new Exercicio();
        session("textoExercicio", exercicio.pegaExercicioAleatorio());

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

    public static Result criaExercicio(){
        exercicio = new Exercicio();
        exercicio.createExercicioPadrao();

        return redirect(routes.Application.solucoes());
    }

}
