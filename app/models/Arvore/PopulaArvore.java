package models.Arvore;

import java.util.ArrayList;
import java.util.Stack;

public class PopulaArvore {
    public Arvore populaArvoreAPartirDeUmPostFix(ArrayList<String> postfix) {
        Arvore arvore = new Arvore();
        Stack<Nodo> pilhaDeNodos = new Stack<Nodo>();
        for (String elemento : postfix) {
            if (isOperador(elemento)) {
                Nodo operandoDaDireita = pilhaDeNodos.pop();
                Nodo operandoDaEsquerda = pilhaDeNodos.pop();
                Nodo nodoAPartirDosPops = new Nodo(elemento);
                nodoAPartirDosPops.adicionaFilho(operandoDaEsquerda);
                nodoAPartirDosPops.adicionaFilho(operandoDaDireita);
                pilhaDeNodos.push(nodoAPartirDosPops);
            } else {
                Nodo operando = new Nodo(elemento);
                pilhaDeNodos.push(operando);
            }
        }
        arvore.setRaiz(pilhaDeNodos.pop());
        return arvore;
    }

    public boolean isOperador(String token) {
        return token.equals("+") ||
        token.equals("-") ||
        token.equals("*") ||
        token.equals("/");
    }
}