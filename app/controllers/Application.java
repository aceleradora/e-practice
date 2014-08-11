package controllers;

import com.avaje.ebean.Ebean;
import models.MensagemDeFeedback;
import models.SolucaoDoExercicio;
import models.exercicioProposto.Exercicio;
import models.exercicioProposto.SeletorAleatorioExercicio;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class Application extends Controller {

    static Form<SolucaoDoExercicio> solucaoDoExercicioForm = Form.form(SolucaoDoExercicio.class);
    private static SolucaoDoExercicio solucaoDoExercicio;
    private static MensagemDeFeedback mensagemDeFeedback;
    private static Exercicio exercicio;
    private static SeletorAleatorioExercicio seletorAleatorioExercicio;

    public Application(SolucaoDoExercicio solucaoDoExercicio) {
        this.solucaoDoExercicio = solucaoDoExercicio;
    }

    public static Result index() {
        return redirect(routes.Application.criaExercicios());
    }

    public static Result selecionaProximoExercicio(){
        setaExercicioNaSecao();
        return redirect(routes.Application.solucoes());
    }

    public static Result solucoes() {
        if(session("exercicio") == null){
            setaExercicioNaSecao();
        }

        List<SolucaoDoExercicio> all = solucaoDoExercicio.all();
        return ok(views.html.index.render(all, solucaoDoExercicioForm));
    }

    public static Result novaSolucao() {
        Form<SolucaoDoExercicio> formPreenchido = solucaoDoExercicioForm.bindFromRequest();

        criaSessaoParaAbas("tabLink3");
        
        if(formPreenchido.hasErrors()){
            flash("status", "Status: erro!");
            return badRequest(views.html.index.render(SolucaoDoExercicio.all(), formPreenchido));
        } else{
            try{
                SolucaoDoExercicio.create(formPreenchido.get());
                mensagemDeFeedback = new MensagemDeFeedback(formPreenchido.get().solucaoDoUsuario);
                flash("solucaoDoUsuario", formPreenchido.get().solucaoDoUsuario);
                flash("mensagemDeFeedback", mensagemDeFeedback.mostraMensagem());
                flash("status", "Status: sua solução foi salva com sucesso!");


                if (mensagemDeFeedback.mostraMensagem().contains("Seu código está correto.")) {
                    exercicio.resolvido = true;
                    exercicio.save();
                }

            } catch (Exception e){
                flash("status", "Status: sua solução não foi salva!");
            }
            return redirect(routes.Application.solucoes());
        }
    }

    public static Result criaExercicios() {
        List<Exercicio> todosExercicios = Ebean.find(Exercicio.class).findList();
        Ebean.delete(todosExercicios);

        Exercicio exercicio1 = new Exercicio();
        exercicio1.enunciado = "Dados 3 valores inteiros 5, 12, e 20, calcule:\n" +
                "    a) A soma dos três valores;\n" +
                "    b) A multiplicação dos 3 valores;\n" +
                "    c) A média aritmética dos três valores.";
        exercicio1.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio1.resolvido = false;
        exercicio1.save();

        Exercicio exercicio2 = new Exercicio();
        exercicio2.enunciado = "As colunas que sustentam a cobertura no Estádio Beira-Rio são de formato\n" +
                "cilindrico, sabendo que as colunas tem 40m de altura e 8 metros de largura, calcule\n" +
                "o volume de cimento usado para construir estas colunas.";
        exercicio2.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio2.resolvido = false;
        exercicio2.save();

        Exercicio exercicio3 = new Exercicio();
        exercicio3.enunciado = "Compute a string resultante de se justapor as palavras \"casa\" com a palavra\n" +
                "\"mento\" e a palavra \"rápido\". ";
        exercicio3.possivelSolucao = new SolucaoDoExercicio("Solução");
        exercicio3.resolvido = false;
        exercicio3.save();

        return redirect(routes.Application.selecionaProximoExercicio());
    }

    public static Result deletaSolucao(int id) {
        SolucaoDoExercicio.delete(id);
        flash("status", "Status: deletado!");

        return redirect(routes.Application.solucoes());
    }

    private static void setaExercicioNaSecao() {
        exercicio = new Exercicio();
        seletorAleatorioExercicio = new SeletorAleatorioExercicio(exercicio);
        exercicio = seletorAleatorioExercicio.buscaExercicio();

        if (exercicio == null) {
            session("exercicio", "Você já resolveu todos os exercícios.");
        } else {
            session("exercicio", exercicio.enunciado);
        }
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

}
