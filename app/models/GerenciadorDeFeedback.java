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
    private String mensagem = "";

    public GerenciadorDeFeedback(String codigo, GerenciadorSintatico gerenciadorSintatico, GerenciadorSemantico gerenciadorSemantico, QuebradorDeCodigoEmLinhas quebradorDeCodigo) {
        this.gerenciadorSintatico = gerenciadorSintatico;
        this.gerenciadorSemantico = gerenciadorSemantico;
        this.quebradorDeCodigo = quebradorDeCodigo;
        this.codigo = this.quebradorDeCodigo.quebra(codigo);
    }

    public String pegaFeedback() {
        for (String linha: codigo){
            gerenciadorSintatico.interpreta(linha);
            mensagem += gerenciadorSintatico.mostraMensagensDeErro();
        }
        if (naoContemErrosSintaticos()){
            for (String linha: codigo){
                gerenciadorSemantico.interpreta(linha);
                mensagem += gerenciadorSemantico.mostraMensagensDeErro();
            }
        }
        return mensagem;
    }

    private boolean naoContemErrosSintaticos() {
        return mensagem.equals("");
    }

}
