package models.resolveOperacoes;

import models.TabelaDeSimbolos;

import java.util.ArrayList;
import java.util.Stack;

public class CalculadoraDeResultado {
    private int primeiroOperando;
    private int segundoOperando;
    private Stack<Integer> pilhaDeNumeros;
    private TabelaDeSimbolos tabelaDeSimbolos;

    public CalculadoraDeResultado(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

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

    private void colocaOperandoNaPilha(String elemento) {
        if (operandoEVariavel(elemento))
            pilhaDeNumeros.push(Integer.parseInt(tabelaDeSimbolos.getValor(elemento)));
        else
            pilhaDeNumeros.push(Integer.parseInt(elemento));
    }

    private boolean operandoEVariavel(String elemento) {
        return tabelaDeSimbolos.simboloExiste(elemento);
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