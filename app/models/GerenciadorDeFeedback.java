package models;

import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.GerenciadorSemantico;
import models.analisadorSemantico.ValidadorDeVariavelDeResultado;
import models.analisadorSintatico.GerenciadorSintatico;

import java.util.ArrayList;

public class GerenciadorDeFeedback {

    private ArrayList<String> codigoQuebrado;
    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private ValidadorDeVariavelDeResultado validadorDeVariavelDeResultado;
    private String mensagemSintatica = "";
    private String mensagemSemantica = "";

    public GerenciadorDeFeedback(String codigo, GerenciadorSintatico gerenciadorSintatico, GerenciadorSemantico gerenciadorSemantico, QuebradorDeCodigoEmLinhas quebradorDeCodigo) {
        this.gerenciadorSintatico = gerenciadorSintatico;
        this.gerenciadorSemantico = gerenciadorSemantico;
        this.quebradorDeCodigo = quebradorDeCodigo;
        this.codigoQuebrado = this.quebradorDeCodigo.quebra(codigo);
        validadorDeVariavelDeResultado = new ValidadorDeVariavelDeResultado(codigoQuebrado);
    }

    public String pegaFeedback() {
        for (String linha: codigoQuebrado) {
            gerenciadorSintatico.interpreta(linha);
            mensagemSintatica += gerenciadorSintatico.mostraMensagensDeErro();
        }
        if (NaoContemErrosSintaticos()) {
            for (String linha: codigoQuebrado) {
                gerenciadorSemantico.interpreta(linha);
                mensagemSemantica += gerenciadorSemantico.mostraMensagensDeErro();
            }
            if (NaoContemErrosSemanticos()) {
                mensagemSemantica = "Seu código está correto.\n";
                if(!(validadorDeVariavelDeResultado.valida()) && !(codigoQuebrado.isEmpty())){
                    mensagemSemantica = validadorDeVariavelDeResultado.retornaMensagemDeErro();
                }else if(codigoQuebrado.isEmpty()){
                    mensagemSemantica = "O terminal está vazio.\n";
                }
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
