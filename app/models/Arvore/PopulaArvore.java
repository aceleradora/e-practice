package models.Arvore;

import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class PopulaArvore {

    IdentificadorDeToken identificadorDeToken;

    ArrayList<String> temp= new ArrayList<String>();

    public Arvore populaArvore(ArrayList<String> postfix) {
        Arvore arvore = new Arvore();
        if(ultimoTokenEhOperador(postfix)) {
            Nodo nodo = new Nodo(postfix.get((postfix.size()-1)));
            arvore.setRaiz(nodo);
        }
        if((postfix.size())==3){
            if(isOperando(postfix,0)){
                arvore.getRaiz().adicionaFilho(new Nodo(postfix.get(0)));
            }
            if(isOperando(postfix,1)){
                arvore.getRaiz().adicionaFilho((new Nodo(postfix.get(1))));

            }
        }

        //Nodo
        //arvore.getRaiz().adicionaFilho();

        return arvore;
        }

    private boolean isOperando(ArrayList<String> postfix, int posicao) {
        identificadorDeToken = new IdentificadorDeToken();
        return identificadorDeToken.identifica(postfix.get(posicao)).equals("IDV") ||
               identificadorDeToken.identifica(postfix.get(posicao)).equals("NUMERO");

    }

    public boolean isOperador(ArrayList<String> postfix, int posicao) {
        return postfix.get(posicao).equals("+") ||
        postfix.get(posicao).equals("-") ||
        postfix.get(posicao).equals("*") ||
        postfix.get(posicao).equals("/");
    }

    private boolean ultimoTokenEhOperador(ArrayList<String> postfix){
        return isOperador(postfix,postfix.size()-1);
    }

}

