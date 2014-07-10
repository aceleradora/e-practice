package controllers;

import play.data.Form;
import play.mvc.*;
import models.*;

import java.util.List;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    static String status;
    static String mensagemFeedbackDeErro;
    private static SolucaoDoExercicio solucaoDoExercicio;
    static FeedBacker feedBacker = new FeedBacker();

    public Application(SolucaoDoExercicio solucaoDoExercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
    }

    public static Result index() {
        status = "";
        mensagemFeedbackDeErro = "";
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        List<SolucaoDoExercicio> all = solucaoDoExercicio.all();

        return ok(views.html.index.render(all, solucaoDoExercicioForm, status, mensagemFeedbackDeErro));
    }

    public static Result novaSolucao(){
        Form<SolucaoDoExercicio> formPreenchido = solucaoDoExercicioForm.bindFromRequest();

        if(formPreenchido.hasErrors()){
            status = "Status: erro!";
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido, status, mensagemFeedbackDeErro));
        } else{
            try{
                String stringSolucaoDoUsuario = formPreenchido.get().solucaoDoUsuario;

                mensagemFeedbackDeErro = feedBacker.feedBackDoCodigoDoUsuario(stringSolucaoDoUsuario);
                status = "Status: sua solução foi salva com sucesso!";

                if (mensagemFeedbackDeErro == ""){
                    SolucaoDoExercicio.create(formPreenchido.get());
                }
            } catch (Exception e){
                status = "Status: sua solução não foi salva!";
            }
            return redirect(routes.Application.solucoes());
        }
    }

    public static Result deletaSolucao(int id){
        SolucaoDoExercicio.delete(id);
        status = "Status: deletado!";
        return redirect(routes.Application.solucoes());
    }
}