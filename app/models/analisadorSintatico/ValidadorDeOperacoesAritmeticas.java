package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas implements Validador {
    private IdentificadorDeToken identificadorDeTokens;
    private ArrayList<String> tokens;
    private ArrayList<String> erros;


    public ValidadorDeOperacoesAritmeticas() {
        identificadorDeTokens = new IdentificadorDeToken();
    }

    private boolean validaSeEhNumeroOuVariavel(String token) {
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

    @Override
    public boolean valida(ArrayList<String> listaDeTokens) {
        tokens = listaDeTokens;

        String tokenEh = "VARIAVEL";
        boolean valida = utilizacaoDeParentesesEstaCorreta();

        if (utilizacaoDeParentesesEstaCorreta()) {
            for (int i = 0; i < tokens.size(); i++) {
                if (!tokenEhParenteses(tokens.get(i))){
                    if(tokenEh.equals("VARIAVEL")) {
                        valida = validaSeEhNumeroOuVariavel(tokens.get(i));
                        tokenEh = "OPERADOR";
                    } else {
                        if(i != tokens.size()-1) {
                            valida = validaSeEhOperador(tokens.get(i)) && validaSeEhNumeroOuVariavel(tokens.get(i + 1));
                            tokenEh = "VARIAVEL";
                        } else{
                            return false;
                        }
                    }

                }
            }
        }
        return valida;
    }

    private boolean utilizacaoDeParentesesEstaCorreta() {
        return aberturaEFechamentoDeParentesesEstaCorreta() && temExpressaoDentroDoParenteses();
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