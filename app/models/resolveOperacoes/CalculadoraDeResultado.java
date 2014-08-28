package models.resolveOperacoes;

import java.util.ArrayList;
import java.util.Stack;

public class CalculadoraDeResultado {
    private int primeiroOperando;
    private int segundoOperando;
    private Stack<Integer> pilhaDeNumeros;

    public int calculaOperacaoAPartirDoPostFix(ArrayList<String> postfix) {
        pilhaDeNumeros = new Stack<Integer>();
        for (String elemento : postfix) {
            if (isOperador(elemento)) {
                retiraOsDoisUltimosNumerosDaPilha();
                devolveParaAPilhaOResultadoDaOperacao(elemento);
            } else {
                colocaOperandoNaPilha(elemento);
            }
        }
        return pilhaDeNumeros.pop();
    }

    private Integer colocaOperandoNaPilha(String elemento) {
        return pilhaDeNumeros.push(Integer.parseInt(elemento));
    }

    private Integer devolveParaAPilhaOResultadoDaOperacao(String elemento) {
        return pilhaDeNumeros.push(realizaOperacaoAdequada(elemento));
    }

    private int realizaOperacaoAdequada(String operacao) {
        if (operacao.equals("+"))
            return primeiroOperando + segundoOperando;
        else if ((operacao.equals("*")))
            return primeiroOperando * segundoOperando;
        else if (operacao.equals("-"))
            return primeiroOperando - segundoOperando;
        else
            return primeiroOperando / segundoOperando;
    }

    private void retiraOsDoisUltimosNumerosDaPilha() {
        segundoOperando = pilhaDeNumeros.pop();
        primeiroOperando = pilhaDeNumeros.pop();
    }

    private boolean isOperador(String elemento) {
        return elemento.equals("+") || elemento.equals("*")
                || elemento.equals("-") || elemento.equals("/");
    }
}