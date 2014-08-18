package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas implements Validador {
    private IdentificadorDeToken identificadorDeTokens;
    private ArrayList<String> tokens;
    private boolean proximoOperandoPodeSerUnario;

    public ValidadorDeOperacoesAritmeticas() {
        identificadorDeTokens = new IdentificadorDeToken();
    }

    public boolean valida(ArrayList<String> listaDeTokens) {
        tokens = listaDeTokens;
        return testaExpressao();
    }

    private boolean testaExpressao(){
        String tokenEh = "OPERANDO";
        proximoOperandoPodeSerUnario = false;
        boolean valida = utilizacaoDeParentesesEstaCorreta();
        if (!valida)
            return false;
        for (int i = 0; i < tokens.size(); i++) {
            if (!tokenEhParenteses(tokens.get(i))) {
                String tokenAtual = identificaSeTokenEhOperandoOuOperador(tokens.get(i));
                if (proximoOperandoPodeSerUnario && (tokens.get(i).equals("+") || tokens.get(i).equals("-"))) {
                    valida = true;
                } else if (tokenAtual.equals(tokenEh)) {
                    valida = true;
                    tokenEh = comutaTokenEh(tokenEh);
                } else {
                    return false;
                }
                setaFlagUnarioDeAcordoComIndiceDeToken(i);
            }
        }
        if (ultimoTokenForUmOperador(tokenEh))
            valida = false;
        return valida;
    }

    private void setaFlagUnarioDeAcordoComIndiceDeToken(int indice) {
        if (tokenForOperadorDiferenteDeSomaOuSubtracao(indice) || tokenForSomaOuSubtracaoEOProximoForParentesesAbrindo(indice)) {
            proximoOperandoPodeSerUnario = true;
        } else {
            proximoOperandoPodeSerUnario = false;
        }
    }

    private boolean tokenForSomaOuSubtracaoEOProximoForParentesesAbrindo(int indice) {
        return ((tokens.get(indice).equals("+") || tokens.get(indice).equals("-")) && (indice < tokens.size() - 1) && tokens.get(indice + 1).equals("("));
    }

    private boolean tokenForOperadorDiferenteDeSomaOuSubtracao(int indice) {
        return tokens.get(indice).equals("*") || tokens.get(indice).equals("/") || tokens.get(indice).equals("=");
    }

    private boolean ultimoTokenForUmOperador(String tokenEh) {
        return tokenEh.equals("OPERANDO");
    }

    private boolean utilizacaoDeParentesesEstaCorreta() {
        return aberturaEFechamentoDeParentesesEstaCorreta() && temExpressaoDentroDoParenteses();
    }

    private boolean tokenEhParenteses(String token) {
        return identificadorDeTokens.identifica(token).equals("PARENTESES_ABERTO") ||
                identificadorDeTokens.identifica(token).equals("PARENTESES_FECHADO");
    }

    private String identificaSeTokenEhOperandoOuOperador(String token) {
        if (isOperando(token)) {
            return "OPERANDO";
        } else if (isOperador(token)) {
            return "OPERADOR";
        } else {
            return "ERRO";
        }
    }

    private String comutaTokenEh(String tokenEh) {
        if (tokenEh.equals("OPERANDO")) {
            return "OPERADOR";
        } else {
            return "OPERANDO";
        }
    }

    public boolean aberturaEFechamentoDeParentesesEstaCorreta() {
        return contadorComparadorDeParenteses() == 0;
    }

    public boolean temExpressaoDentroDoParenteses() {
        if(aberturaEFechamentoDeParentesesEstaCorreta()) {
            for (int i = 0; i < tokens.size(); i++) {
                if (tokenAtualForParentesesAbertoEProximoTokenForParentesesFechando(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean tokenAtualForParentesesAbertoEProximoTokenForParentesesFechando(int i) {
        return tokens.get(i).equals("(") && estaDentroDaListaEProximoTokenForParentesesFechado(i);
    }

    private boolean estaDentroDaListaEProximoTokenForParentesesFechado(int i) {
        return i <= tokens.size() - 2 && tokens.get(i + 1).equals(")");
    }

    private boolean isOperando(String token) {
        return tokenEhIdentificadorDeVariavel(token) || tokenEhNumero(token);
    }

    private boolean isOperador(String token) {
        return isSinalDeAtribuicao(token) || tokenEhUmOperadorValido(token);
    }

    private int contadorComparadorDeParenteses() {
        int contadorDeEquilibrioDeParenteses = 0;

        for(int i = 0; i < tokens.size(); i++) {
            if (contadorDeEquilibrioDeParenteses >= 0) {
                if (tokens.get(i).equals("(")) {
                    contadorDeEquilibrioDeParenteses++;
                }
                if (tokens.get(i).equals(")")) {
                    contadorDeEquilibrioDeParenteses--;
                }
            }
        }
        return contadorDeEquilibrioDeParenteses;
    }

    private boolean tokenEhIdentificadorDeVariavel(String token) {
        return identificadorDeTokens.identifica(token).equalsIgnoreCase("IDV");
    }

    private boolean tokenEhNumero(String token) {
        return identificadorDeTokens.identifica(token).equalsIgnoreCase("NUMERO");
    }

    private boolean tokenEhUmOperadorValido(String token) {
        return identificadorDeTokens.identifica(token).equalsIgnoreCase("ADICAO") ||
                identificadorDeTokens.identifica(token).equalsIgnoreCase("SUBTRACAO") ||
                identificadorDeTokens.identifica(token).equalsIgnoreCase("MULTIPLICACAO") ||
                identificadorDeTokens.identifica(token).equalsIgnoreCase("DIVISAO");
    }

    private boolean isSinalDeAtribuicao(String token) {
        return identificadorDeTokens.identifica(token).equals("IGUAL");
    }

    public String retornaMensagemErro() {
        String mensagem = "";
        if (contadorComparadorDeParenteses() != 0) {
            mensagem = "O número de parenteses abertos não é o mesmo número de parenteses fechados.";
        } else if (!temExpressaoDentroDoParenteses()) {
            mensagem = "Existe(em) parentese(s) que não possui(em) expressão(ões) dentro.";
        } else if(!aberturaEFechamentoDeParentesesEstaCorreta()) {
            mensagem = "Algum(uns) parântese(s) está(ão) no lugar errado ou está(ão) faltando.";
        } else if(!valida(tokens)) {
            mensagem = "Existem erros na expressão aritmetica.";
        }
        return mensagem;
    }
}