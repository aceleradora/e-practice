package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas implements Validador {
    private IdentificadorDeToken identificadorDeTokens;
    private ArrayList<String> tokens;

    public ValidadorDeOperacoesAritmeticas() {
        identificadorDeTokens = new IdentificadorDeToken();
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

    private boolean tokenEhParenteses(String token) {
        return identificadorDeTokens.identifica(token).equals("PARENTESES_ABERTO") ||
                identificadorDeTokens.identifica(token).equals("PARENTESES_FECHADO");
    }

    public  boolean temExpressaoDentroDoParenteses() {
        if(contadorComparadorDeParenteses() == 0) {
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("(")) {
                    if (i <= tokens.size() - 2 && tokens.get(i + 1).equals(")")) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean aberturaEFechamentoDeParentesesEstaCorreta() {
        return contadorComparadorDeParenteses() == 0 ? true : false;
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

    public boolean testaExpressao(){
        String tokenEh = "OPERANDO";
        boolean valida = utilizacaoDeParentesesEstaCorreta();
        if (!valida)
            return false;
        for (int i = 0; i < tokens.size(); i++) {
            if (!tokenEhParenteses(tokens.get(i))) {
                String tokenAtual = identificaSeTokenEhOperandoOuOperador(tokens.get(i));
                if (tokenAtual.equals(tokenEh)) {
                    valida = true;
                    tokenEh = comutaTokenEh(tokenEh);
                } else {
                    return false;
                }
            }
        }
        if (tokenEh.equals("OPERANDO"))
            valida = false;
        return valida;
    }

    private String comutaTokenEh(String tokenEh) {
        if (tokenEh.equals("OPERANDO")) {
            return "OPERADOR";
        } else {
            return "OPERANDO";
        }
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

    private boolean isOperando(String token) {
        return tokenEhIdentificadorDeVariavel(token) || tokenEhNumero(token);
    }

    private boolean isOperador(String token) {
        return isSinalDeAtribuicao(token) || tokenEhUmOperadorValido(token);
    }

    private boolean isSinalDeAtribuicao(String token) {
        return identificadorDeTokens.identifica(token).equals("IGUAL");
    }

    private boolean utilizacaoDeParentesesEstaCorreta() {
        return aberturaEFechamentoDeParentesesEstaCorreta() && temExpressaoDentroDoParenteses();
    }

    public String retornaMensagemErro() {
        String mensagem = "";
        if (contadorComparadorDeParenteses() != 0) {
            mensagem = "O número de parenteses abertos não é o mesmo número de parenteses fechados.";
        } else if (!temExpressaoDentroDoParenteses()) {
            mensagem = "Existe(em) parentese(s) que não possui(em) expressão(ões) dentro.";
        } else if(!aberturaEFechamentoDeParentesesEstaCorreta()) {
            mensagem = "Algum(uns) parântese(s) está(ão) no lugar errado ou está(ão) faltando";
        } else if(!valida(tokens)) {
            mensagem = "Existem erros na expressão aritmetica";
        }
        return mensagem;
    }

    public boolean valida(ArrayList<String> listaDeTokens) {
        tokens = listaDeTokens;
        boolean valida = testaExpressao();
        return valida;
    }
}
