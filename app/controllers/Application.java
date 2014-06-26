package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);

    public static Result index() {

        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        return ok(views.html.index.render(SolucaoDoExercicio.all(), solucaoDoExercicioForm));
    }


    public static Result  novaSolucao(){
        return TODO;
    }

    public static Result deletaSolucao(int id){
        return TODO;
    }

}

