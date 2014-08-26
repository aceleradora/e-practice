package models.arvore;

import java.util.ArrayList;
import java.util.Stack;

public class PopuladorDeArvore {
    private Arvore arvore;
    private Nodo operandoDaDireita;
    private Nodo operandoDaEsquerda;
    private Nodo nodoAPartirDosPops;
    private Stack<Nodo> pilhaDeNodos;

    public Arvore populaArvoreAPartirDeUmPostFix(ArrayList<String> postfix) {
        inicializaAtributos();
        for (String elemento : postfix) {
            if (isOperador(elemento)) {
                retiraOsDoisUltimosNodosDaPilha();
                criaNodoDoElementoOperadorComOsNodosRetiradosDaPilha(elemento);
                colocaNodoCriadoNaPilha();
            } else {
                colocaNodoDeOperandoNaPilha(elemento);
            }
        }
        criaArvoreAPartirDosNodosDaPilha();
        return arvore;
    }

    private void criaArvoreAPartirDosNodosDaPilha() {
        arvore.setRaiz(pilhaDeNodos.pop());
    }

    private void inicializaAtributos() {
        arvore = new Arvore();
        pilhaDeNodos = new Stack<Nodo>();
    }

    private Nodo colocaNodoDeOperandoNaPilha(String elemento) {
        return pilhaDeNodos.push(new Nodo(elemento));
    }

    private Nodo colocaNodoCriadoNaPilha() {
        return pilhaDeNodos.push(nodoAPartirDosPops);
    }

    private void criaNodoDoElementoOperadorComOsNodosRetiradosDaPilha(String elemento) {
        nodoAPartirDosPops = new Nodo(elemento);
        nodoAPartirDosPops.adicionaFilho(operandoDaEsquerda);
        nodoAPartirDosPops.adicionaFilho(operandoDaDireita);
    }

    private void retiraOsDoisUltimosNodosDaPilha() {
        operandoDaDireita = pilhaDeNodos.pop();
        operandoDaEsquerda = pilhaDeNodos.pop();
    }

    private boolean isOperador(String token) {
        return token.equals("+") ||
        token.equals("-") ||
        token.equals("*") ||
        token.equals("/");
    }
}