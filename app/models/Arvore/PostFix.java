package models.Arvore;

import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class PostFix {
    IdentificadorDeToken identificadorDeToken;
    Stack<String> pilha = new Stack<String>();

    ArrayList<String> postFix = new ArrayList<String>();

    public PostFix(){
        identificadorDeToken = new IdentificadorDeToken();
    }

    public ArrayList<String> getPostFix() {
        return postFix;
    }
    public ArrayList criaPosfix(ArrayList<String> tokens) {
        for(int i = 0; i < tokens.size(); i++) {

            if(identificadorDeToken.identifica(tokens.get(i)).equals("NUMERO") ||
                identificadorDeToken.identifica(tokens.get(i)).equals("IDV")) {
                    postFix.add(tokens.get(i));
            }

            else if(identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_ABERTO")){
                pilha.push(tokens.get(i));
            }

            else if(identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_FECHADO")) {
                while (!identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_ABERTO")){
                    if(!identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_ABERTO"))
                        postFix.add(pilha.pop());
                }
                if(identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_ABERTO")){
                    pilha.pop();
                }
            }

            else if(!pilha.empty()) {
                if(identificadorDeToken.identifica(pilha.peek()).equals("MULTIPLICACAO") ||
                    identificadorDeToken.identifica(pilha.peek()).equals("DIVISAO")) {
                    postFix.add(pilha.pop());
                    pilha.push(tokens.get(i));
                }
                else{
                    pilha.push(tokens.get(i));
                }
            }
            else if(identificadorDeToken.identifica(tokens.get(i)).equals("ADICAO") ||
                    identificadorDeToken.identifica(tokens.get(i)).equals("SUBTRACAO") ||
                    identificadorDeToken.identifica(tokens.get(i)).equals("MULTIPLICACAO") ||
                    identificadorDeToken.identifica(tokens.get(i)).equals("DIVISAO")) {
                        pilha.push(tokens.get(i));
            }
        }

        while(!pilha.empty()) {
            if(identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_FECHADO")){
                pilha.pop();
            }
            postFix.add(pilha.pop());
        }

        return postFix;

    }

}
