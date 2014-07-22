package models;

import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSintatico.GerenciadorDeValidacao;

import java.util.ArrayList;

public class GerenciadorDeFeedback {

    private ArrayList<String> codigo;
    private GerenciadorDeValidacao gerenciadorDeValidacao;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private String mensagem = "";

    public GerenciadorDeFeedback(String codigo, GerenciadorDeValidacao gerenciadorDeValidacao, QuebradorDeCodigoEmLinhas quebradorDeCodigo) {
        this.gerenciadorDeValidacao = gerenciadorDeValidacao;
        this.quebradorDeCodigo = quebradorDeCodigo;
        this.codigo = this.quebradorDeCodigo.quebra(codigo);
    }

    public String pegaFeedback() {
        for (String linha: codigo){
            gerenciadorDeValidacao.interpreta(linha);
            mensagem += gerenciadorDeValidacao.mostraMensagensDeErro();
        }

        if(codigo.equals("var x = String")){
            return "Erro!!";
        }

        return mensagem;
    }


}
