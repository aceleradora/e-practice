package controllers;

import play.*;
import play.mvc.*;
import models.SolucaoDoExercicio;
import views.html.*;

public class Application extends Controller {


    public static Result index() {

        return ok(index.render("e-Pr@ctice"));
    }


    public static void  salvarCodigo(String codigo){

        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);
        SolucaoDoExercicio.create(solucaoDoExercicio);
    }


    }

