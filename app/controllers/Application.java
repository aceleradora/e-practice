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

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    private static SolucaoDoExercicio solucaoDoExercicio;
    private static MensagemDeFeedback mensagemDeFeedback;
    private static Exercicio exercicio;
    private static SeletorAleatorioExercicio seletorAleatorioExercicio;
    private static int idAtual = 0;
    public Application(SolucaoDoExercicio solucaoDoExercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
    }

    public static Result index() {
        criaNovoUsuario();
        return redirect(routes.Application.selecionaProximoExercicio());
    }

    public static Result selecionaProximoExercicio(){
        criaNovoUsarioCasoNaoExistaUmUsuario();
        setaExercicioNaSecao();
        Aba.criaSessaoParaAbas("tabLink1");
        if (existeExercicioNoBanco()) {
            session("resultadoDoProfessor", exercicio.resultadoDoProfessor);
            session("solucaoDoProfessor", exercicio.solucaoDoProfessor);
        }

        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes() {
        if(exercicio == null){
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

                mensagemDeFeedback = new MensagemDeFeedback(formPreenchido.get().solucaoDoUsuario, exercicio);

                mensagemFlashDeSucesso(formPreenchido);

                buscaUsuarioNoBanco().seNaoHouverExercicioResolvidoAdicionaExercicio(exercicio);

            } catch (Exception e){
                mensagemFlashDeErro(formPreenchido, e);

                if(solucaoDoExercicio != null) {
                    solucaoDoExercicio.save();
                }
            }
            return redirect(routes.Application.solucoes());
        }
    }

    private static void setaExercicioNaSecao() {
        criaNovoUsarioCasoNaoExistaUmUsuario();
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
        seletorAleatorioExercicio = new SeletorAleatorioExercicio(buscaUsuarioNoBanco());
        exercicio = seletorAleatorioExercicio.buscaExercicioNaoResolvido();
    }

    private static void mensagemFlashDeErro(Form<SolucaoDoExercicio> formPreenchido, Exception e) {
        flash("status", "Erro: Sintaxe não reconhecida." + e.getMessage());
        e.printStackTrace();
        flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);
    }

    private static void mensagemFlashDeSucesso(Form<SolucaoDoExercicio> formPreenchido) {
        flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);
        flash("mensagemDeFeedback", mensagemDeFeedback.mostraMensagem());
        flash("status", "Status: sua solução foi salva com sucesso!");
    }

    private static boolean existeMaisDeUmExercicioNoBanco() {
        return buscaUsuarioNoBanco().todosNaoResolvidos().size() > 1;
    }

    private static boolean exercicioSorteadoForOMesmoQueEstavaNaSessao() {
        return idAtual == exercicio.id;
    }

    private static boolean existeExercicioNoBanco() {
        return exercicio != null;
    }

    private static void criaNovoUsuario() {
        Usuario usuario = new Usuario();
        usuario.save();
        String idDoUsuario = Integer.toString(usuario.id);
        session("idDoUsuario", idDoUsuario);
    }

    private static void criaNovoUsarioCasoNaoExistaUmUsuario() {
        if(buscaUsuarioNoBanco() == null) {
            criaNovoUsuario();
        }
    }

    private static Usuario buscaUsuarioNoBanco(){
        String idDoUsuario = session("idDoUsuario");
        Usuario usuario = Usuario.achaUsuarioPorId(idDoUsuario);
        return usuario;
    }

}