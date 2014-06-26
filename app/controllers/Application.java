package controllers;

import play.*;
import play.mvc.*;
import models.SolucaoDoExercicio;
import views.html.*;

public class Application extends Controller {


    public static Result index() {

        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        return TODO;
    }


    public static Result  novaSolucao(){
        return TODO;
    }

    public static Result deletaSolucao(int id){
        return TODO;
    }

}

