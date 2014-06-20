package controllers;

import play.*;
import play.mvc.*;
import models.SolucaoDoExercicio;
import views.html.*;

public class Application extends Controller {


    public static Result index() {

        return ok(index.render("e-Pr@ctice"));
    }


    public static String  salvarCodigo(String codigo){

        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio(codigo);
        return SolucaoDoExercicio.create(solucaoDoExercicio);
    }


    }

