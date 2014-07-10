package controllers;

import play.data.Form;
import play.mvc.*;
import models.*;

import java.util.List;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    static String status;
    static String mensagemFeedback;
    private static SolucaoDoExercicio solucaoDoExercicio;
    static FeedBacker feedBacker = new FeedBacker();

    public Application(SolucaoDoExercicio solucaoDoExercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
    }

    public static Result index() {
        status = "";
        mensagemFeedback = "";
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes(){
        List<SolucaoDoExercicio> all = solucaoDoExercicio.all();

        return ok(views.html.index.render(all, solucaoDoExercicioForm, status, mensagemFeedback));
    }

    public static Result novaSolucao(){
        Form<SolucaoDoExercicio> formPreenchido = solucaoDoExercicioForm.bindFromRequest();

        if(formPreenchido.hasErrors()){
            status = "Status: erro!";
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido, status, mensagemFeedback));
        } else{
            try{
                String teste = formPreenchido.get().solucaoDoUsuario;

                mensagemFeedback = feedBacker.feedBackDoCodigoDoUsuario(teste);
                status = "Status: sua solução foi salva com sucesso!";

                if (mensagemFeedback == ""){
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