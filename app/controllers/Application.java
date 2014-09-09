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
    private static SeletorAleatorioExercicio seletorAleatorioExercicio;

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
            session("resultadoDoProfessor", buscaExercicioNoBanco().resultadoDoProfessor);
            session("solucaoDoProfessor", buscaExercicioNoBanco().solucaoDoProfessor);
        }

        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes() {
        if(buscaExercicioNoBanco() == null){
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

                mensagemDeFeedback = new MensagemDeFeedback(formPreenchido.get().solucaoDoUsuario, buscaExercicioNoBanco());

                mensagemFlashDeSucesso(formPreenchido);

                buscaUsuarioNoBanco().seNaoHouverExercicioResolvidoAdicionaExercicio(buscaExercicioNoBanco());

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
                    session("idDoExercicio", String.valueOf(seletorAleatorioExercicio.buscaExercicioNaoResolvido().id));
                }
            }
            String idDoExercicioAtualNaSessao = session("idDoExercicio");
            session("idAtualDoExercicio", idDoExercicioAtualNaSessao);
            session("exercicio", buscaExercicioNoBanco().enunciado);
        } else {
            session("exercicio", "Você já resolveu todos os exercícios.");
        }
    }

    private static void inicializaExercicioESeletorAleatorio() {
        Exercicio exercicio;
        seletorAleatorioExercicio = new SeletorAleatorioExercicio(buscaUsuarioNoBanco());
        exercicio = seletorAleatorioExercicio.buscaExercicioNaoResolvido();
        session("idDoExercicio", String.valueOf(exercicio.id));
    }

    private static void mensagemFlashDeErro(Form<SolucaoDoExercicio> formPreenchido, Exception e) {
        flash("status", "Erro: Sintaxe não reconhecida." + e.getMessage());
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
        if (session("idAtualDoExercicio") == null || session("idDoExercicio") == null){
            return false;
        }
        return session("idAtualDoExercicio").equals(session("idDoExercicio"));
    }

    private static boolean existeExercicioNoBanco() {
        return buscaExercicioNoBanco() != null;
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

    private static Exercicio buscaExercicioNoBanco(){
        String idDoExercicio = session("idDoExercicio");
        Exercicio exercicio = Exercicio.achaExercicioPorId(idDoExercicio);
        return exercicio;
    }

}