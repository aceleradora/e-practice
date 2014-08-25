package models.Arvore;

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
                reitraOsDoisUltimosNodosDaPilha();
                criaNodoDoElementoOperadorComOsNodosRemovidosDaPilha(elemento);
                devolveNodoCriadoParaAPilha();
            } else {
                adicionaElementoParaAPilha(elemento);
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

    private Nodo adicionaElementoParaAPilha(String elemento) {
        return pilhaDeNodos.push(new Nodo(elemento));
    }

    private Nodo devolveNodoCriadoParaAPilha() {
        return pilhaDeNodos.push(nodoAPartirDosPops);
    }

    private void criaNodoDoElementoOperadorComOsNodosRemovidosDaPilha(String elemento) {
        nodoAPartirDosPops = new Nodo(elemento);
        nodoAPartirDosPops.adicionaFilho(operandoDaEsquerda);
        nodoAPartirDosPops.adicionaFilho(operandoDaDireita);
    }

    private void reitraOsDoisUltimosNodosDaPilha() {
        operandoDaDireita = pilhaDeNodos.pop();
        operandoDaEsquerda = pilhaDeNodos.pop();
    }

    public boolean isOperador(String token) {
        return token.equals("+") ||
        token.equals("-") ||
        token.equals("*") ||
        token.equals("/");
    }
}