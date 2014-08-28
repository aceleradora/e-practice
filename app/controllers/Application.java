package controllers;

import models.MensagemDeFeedback;
import models.SolucaoDoExercicio;
import models.Usuario;
import models.exercicioProposto.Exercicio;
import models.exercicioProposto.SeletorAleatorioExercicio;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    private static SolucaoDoExercicio solucaoDoExercicio;
    private static MensagemDeFeedback mensagemDeFeedback;
    private static Exercicio exercicio;
    private static SeletorAleatorioExercicio seletorAleatorioExercicio;
    private static int idAtual = 0;
    private static Usuario usuario;
    public Application(SolucaoDoExercicio solucaoDoExercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
    }

    public static Result index() {
        usuario = new Usuario();
        usuario.save();
        return redirect(routes.Application.selecionaProximoExercicio());
    }

    public static Result selecionaProximoExercicio(){
        setaExercicioNaSecao();
        Aba.criaSessaoParaAbas("tabLink1");
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes() {
        if(session("exercicio") == null){
            setaExercicioNaSecao();
        }

        List<SolucaoDoExercicio> all = solucaoDoExercicio.all();
        return ok(views.html.index.render(all, solucaoDoExercicioForm));
    }

    public static Result novaSolucao() throws Exception {
        Form<SolucaoDoExercicio> formPreenchido = solucaoDoExercicioForm.bindFromRequest();

        Aba.criaSessaoParaAbas("tabLink3");

        if(formPreenchido.hasErrors()){
            flash("status", "Status: erro!");
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido));
        } else{
            try{
                Map<String, String> solucao = formPreenchido.data();
                SolucaoDoExercicio solucaoDoUsuario = new SolucaoDoExercicio(solucao.get("solucaoDoUsuario"));
                solucaoDoUsuario.setExercicio(exercicio);
                solucaoDoUsuario.idDoUsuario = usuario.id;

                solucaoDoUsuario.save();

                mensagemDeFeedback = new MensagemDeFeedback(formPreenchido.get().solucaoDoUsuario);

                flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);
                flash("mensagemDeFeedback", mensagemDeFeedback.mostraMensagem());
                flash("status", "Status: sua solução foi salva com sucesso!");

                if(!usuario.exerciciosResolvidos.contains(exercicio)) {
                    usuario.exerciciosResolvidos.add(exercicio);
                    usuario.save();
                }

            } catch (Exception e){
                flash("status", "Erro: Sintaxe não reconhecida.");
                flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);

                if(solucaoDoExercicio != null) {
                    solucaoDoExercicio.save();
                }
            }
            return redirect(routes.Application.solucoes());
        }
    }

    public static Result deletaSolucao(int id) {
        SolucaoDoExercicio.delete(id);
        flash("status", "Status: deletado!");

        return redirect(routes.Application.solucoes());
    }

    private static void setaExercicioNaSecao() {
        inicializaExercicioESeletorAleatorio();

        if (existeExercicioNoBanco()) {
            if (existeMaisDeUmExercicioNoBanco()) {
                while (exercicioSorteadoForOMesmoQueEstavaNaSessao()) {
                    exercicio = seletorAleatorioExercicio.buscaExercicioNaoResolvido();
                }
            }
            idAtual = exercicio.id;
            session("exercicio", exercicio.enunciado);
        } else {
            session("exercicio", "Você já resolveu todos os exercícios.");
        }
    }

    private static void inicializaExercicioESeletorAleatorio() {
        exercicio = new Exercicio();
        seletorAleatorioExercicio = new SeletorAleatorioExercicio(usuario);
        exercicio = seletorAleatorioExercicio.buscaExercicioNaoResolvido();
    }

    private static boolean existeMaisDeUmExercicioNoBanco() {
        return usuario.todosNaoResolvidos().size() > 1;
    }

    private static boolean exercicioSorteadoForOMesmoQueEstavaNaSessao() {
        return idAtual == exercicio.id;
    }

    private static boolean existeExercicioNoBanco() {
        return exercicio != null;
    }
}