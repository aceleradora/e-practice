package models.Arvore;

import java.util.ArrayList;

public class PopulaArvore {
    public Arvore populaArvoreAPartirDeUmPostFix(ArrayList<String> postfix) {
        Arvore arvore = new Arvore();
        Nodo nodoAuxiliar = null;
        int posicaoDoUltimoOperando = 0;

        for (int i = 0; i < postfix.size(); i++) {
            if (isOperador(postfix.get(i))) {
                if (nodoAuxiliar == null) {
                    nodoAuxiliar = new Nodo(postfix.get(i));
                    nodoAuxiliar.adicionaFilho(new Nodo(postfix.get(i - 2)));
                    nodoAuxiliar.adicionaFilho(new Nodo(postfix.get(i - 1)));
                    posicaoDoUltimoOperando = i - 2;
                } else {
                    Nodo temp = new Nodo(postfix.get(i));
                    temp.adicionaFilho(nodoAuxiliar);
                    temp.adicionaFilho(new Nodo(postfix.get(posicaoDoUltimoOperando - 1)));
                    nodoAuxiliar = temp;
                    posicaoDoUltimoOperando -= 1;
                }
            }
        }
        arvore.setRaiz(nodoAuxiliar);
        return arvore;
    }

    public boolean isOperador(String token) {
        return token.equals("+") ||
        token.equals("-") ||
        token.equals("*") ||
        token.equals("/");
    }
}