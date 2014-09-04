package models;

import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.GerenciadorSemantico;
import models.analisadorSemantico.ValidadorDeVariavelDeResultado;
import models.analisadorSintatico.GerenciadorSintatico;
import models.exercicioProposto.Exercicio;

import java.util.ArrayList;

public class GerenciadorDeFeedback {
    private ArrayList<String> codigoQuebrado;
    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigo;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ValidadorDeVariavelDeResultado validadorDeVariavelDeResultado;
    private String mensagemSintatica = "";
    private String mensagemSemantica = "";
    private String resultadoCalculado = "";
    private Exercicio exercicio;

    public GerenciadorDeFeedback(String codigo, GerenciadorSintatico gerenciadorSintatico, GerenciadorSemantico gerenciadorSemantico, QuebradorDeCodigoEmLinhas quebradorDeCodigo, TabelaDeSimbolos tabelaDeSimbolos, Exercicio exercicio) {
        this.gerenciadorSintatico = gerenciadorSintatico;
        this.gerenciadorSemantico = gerenciadorSemantico;
        this.quebradorDeCodigo = quebradorDeCodigo;
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.codigoQuebrado = this.quebradorDeCodigo.quebra(codigo);
        this.exercicio = exercicio;
        validadorDeVariavelDeResultado = new ValidadorDeVariavelDeResultado(codigoQuebrado);
    }

    public String pegaFeedback() {
        pegaErrosSintaticos();
        if (NaoContemErrosSintaticos()) {
            pegaErrosSemanticos();
            if (NaoContemErrosSemanticos()) {
                mensagemSemantica = "Seu código está correto.\n";
                if(temCodigoNoTerminalMasNaoTemVariavelDeResultado()){
                    mensagemSemantica = validadorDeVariavelDeResultado.retornaMensagemDeErro();
                }else if(codigoQuebrado.isEmpty()){
                    mensagemSemantica = "O terminal está vazio.\n";
                }
            }
        }

        ArrayList<String> resultadosDoUsuario = new ArrayList<String>();

        for (String variavelDeResultado : tabelaDeSimbolos.getVariaveisDeResultado()) {
            resultadoCalculado += "Sua resposta: " + tabelaDeSimbolos.getValor(variavelDeResultado) + " \n";
            resultadosDoUsuario.add(tabelaDeSimbolos.getValor(variavelDeResultado));
        }

        ValidadorDeValorDeResultado validadorDeValorDeResultado = new ValidadorDeValorDeResultado(exercicio);
        boolean usuarioAcertouOsResultados = validadorDeValorDeResultado.comparaResultados(resultadosDoUsuario);
        if (!usuarioAcertouOsResultados && NaoContemErrosSintaticos() && mensagemSemantica.equals("Seu código está correto.\n")) {
            mensagemSemantica = "Resposta incorreta!\n" + resultadoCalculado;
        }

        if (mensagemSemantica.equals("Seu código está correto.\n"))
            return mensagemSintatica + mensagemSemantica + resultadoCalculado;
        else
            return mensagemSintatica + mensagemSemantica;
    }

    private boolean temCodigoNoTerminalMasNaoTemVariavelDeResultado() {
        return !(validadorDeVariavelDeResultado.valida()) && !(codigoQuebrado.isEmpty());
    }

    private void pegaErrosSemanticos() {
        for (String linha: codigoQuebrado) {
            gerenciadorSemantico.interpreta(linha);
            mensagemSemantica += gerenciadorSemantico.mostraMensagensDeErro();
        }
    }

    private void pegaErrosSintaticos() {
        for (String linha: codigoQuebrado) {
            gerenciadorSintatico.interpreta(linha);
            mensagemSintatica += gerenciadorSintatico.mostraMensagensDeErro();
        }
    }

    private boolean NaoContemErrosSintaticos() {
        return mensagemSintatica.equals("");
    }

    private boolean NaoContemErrosSemanticos() {
        return mensagemSemantica.equals("");
    }
}
