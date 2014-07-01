package controllers;

import play.data.Form;
import play.mvc.*;
import models.*;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    static String status;

    public static Result index() {
        status = "";
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        return ok(views.html.index.render(SolucaoDoExercicio.all(), solucaoDoExercicioForm, status));
    }

    public static Result  novaSolucao(){
        Form<SolucaoDoExercicio> formPreenchido = solucaoDoExercicioForm.bindFromRequest();

        if(formPreenchido.hasErrors()){
            status = "Status: erro!";
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido, status));
        } else{
            SolucaoDoExercicio.create(formPreenchido.get());
            status = "Status: enviado!";
            return redirect(routes.Application.solucoes());
        }
    }

    public static Result deletaSolucao(int id){
        SolucaoDoExercicio.delete(id);
        status = "Status: deletado!";
        return redirect(routes.Application.solucoes());
    }
}