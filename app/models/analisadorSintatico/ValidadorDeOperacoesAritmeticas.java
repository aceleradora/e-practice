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

    private boolean validaSeEhVariavel(String token) {
        return (tokenEhNumero(token) || tokenEhIdentificadorDeVariavel(token)) ? true : false;
    }

    private boolean tokenEhIdentificadorDeVariavel(String token) {
        return identificadorDeTokens.identifica(token).equalsIgnoreCase("IDV");
    }

    private boolean tokenEhNumero(String token) {
        return identificadorDeTokens.identifica(token).equalsIgnoreCase("NUMERO");
    }

    private boolean validaSeEhOperador(String token) {
        return (tokenEhUmOperadorValido(token)) ? true : false;
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
        int quantidadeDeParentesesAbertos = 0;

        if(contadorComparadorDeParenteses() == 0) {
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("(")) {
                    quantidadeDeParentesesAbertos++;
                }
                if (tokens.get(i).equals(")")) {
                    if (quantidadeDeParentesesAbertos > 0) {
                        quantidadeDeParentesesAbertos--;
                    } else {
                        return false;
                    }
                }
            }
            return quantidadeDeParentesesAbertos == 0;
        }
        return false;
    }

    private int contadorComparadorDeParenteses() {
        int contadorDeEquilibrioDeParenteses = 0;

        for(int i = 0; i < tokens.size(); i++){
            if(tokens.get(i).equals("(")) {
                contadorDeEquilibrioDeParenteses++;
            }
            if (tokens.get(i).equals(")")) {
                contadorDeEquilibrioDeParenteses--;
            }
        }
        return contadorDeEquilibrioDeParenteses;
    }

    @Override
    public boolean valida(ArrayList<String> listaDeTokens) {
        tokens = listaDeTokens;

        String tipoToken = "VARIAVEL";
        boolean parentesesOk = aberturaEFechamentoDeParentesesEstaCorreta() && temExpressaoDentroDoParenteses();
        boolean validador = parentesesOk;

        if(parentesesOk) {
            for (int i = 0; i < tokens.size(); i++) {
                if(!tokenEhParenteses(tokens.get(i)) && validador == true) {
                    if(tipoToken.equals("VARIAVEL")) {
                        validador = validaSeEhVariavel(tokens.get(i));
                        tipoToken = "OPERADOR";
                    }
                    else {
                        validador = validaSeEhOperador(tokens.get(i));
                        tipoToken = "VARIAVEL";
                    }
                }
            }
        }
        return validador;
    }

    @Override
    public String retornaMensagemErro() {
        String mensagem = "";
        if (contadorComparadorDeParenteses() != 0) {
            mensagem = "O número de parenteses abertos não é o mesmo número de parenteses fechados.";
        } else if (!temExpressaoDentroDoParenteses()) {
            mensagem = "Existe(em) parentese(s) que não possui(em) expressão(ões) dentro.";
        } else if(!aberturaEFechamentoDeParentesesEstaCorreta()) {
            mensagem = "Algum(uns) parântese(s) está(ão) no lugar errado ou está(ão) faltando";
        } else if (!getArrayDeErros()) {
            mensagem = "a segunda palavra deveria ser um identificador de variável válido - ";
        }
        return mensagem;
    }

    private boolean getArrayDeErros() {
        return false;
    }
}