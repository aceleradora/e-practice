package models;

import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSintatico.GerenciadorSintatico;

import java.util.ArrayList;

public class GerenciadorDeFeedback {

    private ArrayList<String> codigo;
    private GerenciadorSintatico gerenciadorDeValidacao;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private String mensagem = "";

    public GerenciadorDeFeedback(String codigo, GerenciadorSintatico gerenciadorDeValidacao, QuebradorDeCodigoEmLinhas quebradorDeCodigo) {
        this.gerenciadorDeValidacao = gerenciadorDeValidacao;
        this.quebradorDeCodigo = quebradorDeCodigo;
        this.codigo = this.quebradorDeCodigo.quebra(codigo);
    }

    public String pegaFeedback() {
        for (String linha: codigo){
            gerenciadorDeValidacao.interpreta(linha);
            mensagem += gerenciadorDeValidacao.mostraMensagensDeErro();
        }

        return mensagem;
    }

}
