package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Aba extends Controller {
    public static Result setaAbaAtual(String aba) {
        criaSessaoParaAbas(aba);

        return ok("{aba-ativa: " + aba + "}");
    }

    public static void criaSessaoParaAbas(String aba) {
        session("tabLink1", "");
        session("tabLink2", "");
        session("tabLink3", "");
        session(aba, "active");
    }
}
