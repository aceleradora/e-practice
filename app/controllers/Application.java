package controllers;

import com.avaje.ebean.Ebean;
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
        session("id", geraNovoIdDeUsuario());
        return redirect(routes.Application.criaExercicios());
    }

    public static Result selecionaProximoExercicio(){
        setaExercicioNaSecao();
        criaSessaoParaAbas("tabLink1");
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

        criaSessaoParaAbas("tabLink3");

        if(formPreenchido.hasErrors()){
            flash("status", "Status: erro!");
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido));
        } else{
            try{
                Map<String, String> solucao = formPreenchido.data();
                SolucaoDoExercicio solucaoDoUsuario = new SolucaoDoExercicio(solucao.get("solucaoDoUsuario"));
                solucaoDoUsuario.setExercicio(exercicio);
                solucaoDoUsuario.idDoUsuario = Integer.parseInt(session("id"));
                solucaoDoUsuario.save();

                mensagemDeFeedback = new MensagemDeFeedback(formPreenchido.get().solucaoDoUsuario);

                flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);
                flash("mensagemDeFeedback", mensagemDeFeedback.mostraMensagem());
                flash("status", "Status: sua solução foi salva com sucesso!");

                exercicio.resolvido = true;
                exercicio.save();

                if(!usuario.exerciciosResolvidos.contains(exercicio)) {
                    usuario.exerciciosResolvidos.add(exercicio);
                }

                usuario.save();

            } catch (Exception e){
                flash("status", "Erro: Sintaxe não reconhecida.");
                flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);

                if(exercicio != null) {
                    exercicio.save();
                }
            }
            return redirect(routes.Application.solucoes());
        }
    }

    public static Result criaExercicios() {
        List<Usuario> todosUsuarios = Ebean.find(Usuario.class).findList();
        List<SolucaoDoExercicio> todasSolucoes = Ebean.find(SolucaoDoExercicio.class).findList();
        List<Exercicio> todosExercicios = Ebean.find(Exercicio.class).findList();

        for(Usuario usuario: todosUsuarios){
            Ebean.deleteManyToManyAssociations(usuario, "exerciciosResolvidos");
        }

        Ebean.delete(todasSolucoes);
        Ebean.delete(todosExercicios);
        Ebean.delete(todosUsuarios);


        Exercicio exercicio1 = new Exercicio();
        exercicio1.enunciado = "Dados 3 valores inteiros 5, 12, e 20, calcule:\n" +
                "a) A soma dos três valores;\n" +
                "b) A multiplicação dos 3 valores;\n" +
                "c) A média aritmética dos três valores.";
        exercicio1.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio1.resolvido = false;
        exercicio1.save();

        Exercicio exercicio2 = new Exercicio();
        exercicio2.enunciado = "As colunas que sustentam a cobertura no Estádio Beira-Rio são de formato " +
                "cilindrico, sabendo que as colunas tem 40m de altura e 8 metros de largura, calcule " +
                "o volume de cimento usado para construir estas colunas.";
        exercicio2.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio2.resolvido = false;
        exercicio2.save();

        Exercicio exercicio3 = new Exercicio();
        exercicio3.enunciado = "Compute a string resultante de se justapor as palavras \"casa\" com a palavra " +
                "\"mento\" e a palavra \"rápido\". ";
        exercicio3.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio3.resolvido = false;
        exercicio3.save();

        usuario = new Usuario();
        usuario.save();

        return redirect(routes.Application.selecionaProximoExercicio());
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
                    exercicio = seletorAleatorioExercicio.buscaExercicioNaoResolvido(usuario);
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
        seletorAleatorioExercicio = new SeletorAleatorioExercicio(exercicio);
        exercicio = seletorAleatorioExercicio.buscaExercicioNaoResolvido(usuario);
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

    public static Result setaAbaAtual(String aba) {
        criaSessaoParaAbas(aba);

        return ok("{aba-ativa: " + aba + "}");
    }

    private static void criaSessaoParaAbas(String aba) {
        session("tabLink1", "");
        session("tabLink2", "");
        session("tabLink3", "");
        session(aba, "active");
    }

    private static String geraNovoIdDeUsuario(){
        Random random = new Random();
        int randomInteiro = random.nextInt();
        return String.valueOf(randomInteiro);
    }

}
