package models;

import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.GerenciadorSemantico;
import models.analisadorSintatico.GerenciadorSintatico;

import java.util.ArrayList;

public class GerenciadorDeFeedback {

    private ArrayList<String> codigo;
    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private String mensagemSintatica = "";
    private String mensagemSemantica = "";

    public GerenciadorDeFeedback(String codigo, GerenciadorSintatico gerenciadorSintatico, GerenciadorSemantico gerenciadorSemantico, QuebradorDeCodigoEmLinhas quebradorDeCodigo) {
        this.gerenciadorSintatico = gerenciadorSintatico;
        this.gerenciadorSemantico = gerenciadorSemantico;
        this.quebradorDeCodigo = quebradorDeCodigo;
        this.codigo = this.quebradorDeCodigo.quebra(codigo);
    }

    public String pegaFeedback() {
        for (String linha: codigo) {
            gerenciadorSintatico.interpreta(linha);
            mensagemSintatica += gerenciadorSintatico.mostraMensagensDeErro();
        }
        if (NaoContemErrosSintaticos()) {
            for (String linha: codigo) {
                gerenciadorSemantico.interpreta(linha);
                mensagemSemantica += gerenciadorSemantico.mostraMensagensDeErro();
            }
            if (NaoContemErrosSemanticos()) {
                mensagemSemantica = "Seu código está correto.\n";
            }
        }
        return mensagemSintatica + mensagemSemantica;
    }

    private boolean NaoContemErrosSintaticos() {
        return mensagemSintatica.equals("");
    }

    private boolean NaoContemErrosSemanticos() {
        return mensagemSemantica.equals("");
    }

}
