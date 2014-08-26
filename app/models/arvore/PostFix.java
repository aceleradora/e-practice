package models.arvore;

import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;
import java.util.Stack;

public class PostFix {
    IdentificadorDeToken identificadorDeToken;
    Stack<String> pilha = new Stack<String>();

    ArrayList<String> postFix = new ArrayList<String>();

    public PostFix(){
        identificadorDeToken = new IdentificadorDeToken();
    }

    public ArrayList criaPosfix(ArrayList<String> tokens) {
        for(int i = 0; i < tokens.size(); i++) {

            if(verificaSeForNumeroOuIDV(tokens, i)) {
                    postFix.add(tokens.get(i));
            }

            else if(verificaSeForParentesesAberto(tokens, i)){
                pilha.push(tokens.get(i));
            }

            else if(verificaSeForParentesesFechado(tokens, i)) {
                while (!identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_ABERTO")){
                    if(!verificaSeForParentesesAberto(tokens, i))
                        postFix.add(pilha.pop());
                }
                if(identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_ABERTO")){
                    pilha.pop();
                }
            }

            else if(!pilha.empty()) {
                if(verificaSeForOperadorPrioritario()) {
                    postFix.add(pilha.pop());
                    pilha.push(tokens.get(i));
                }
                else{
                    pilha.push(tokens.get(i));
                }
            }
            else if(verificaSeForOperador(tokens, i)) {
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

    private boolean verificaSeForParentesesFechado(ArrayList<String> tokens, int i) {
        return identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_FECHADO");
    }

    private boolean verificaSeForParentesesAberto(ArrayList<String> tokens, int i) {
        return identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_ABERTO");
    }

    private boolean verificaSeForNumeroOuIDV(ArrayList<String> tokens, int i) {
        return identificadorDeToken.identifica(tokens.get(i)).equals("NUMERO") ||
            identificadorDeToken.identifica(tokens.get(i)).equals("IDV");
    }

    private boolean verificaSeForOperadorPrioritario() {
        return identificadorDeToken.identifica(pilha.peek()).equals("MULTIPLICACAO") ||
            identificadorDeToken.identifica(pilha.peek()).equals("DIVISAO");
    }

    private boolean verificaSeForOperador(ArrayList<String> tokens, int i) {
        return identificadorDeToken.identifica(tokens.get(i)).equals("ADICAO") ||
                identificadorDeToken.identifica(tokens.get(i)).equals("SUBTRACAO") ||
                identificadorDeToken.identifica(tokens.get(i)).equals("MULTIPLICACAO") ||
                identificadorDeToken.identifica(tokens.get(i)).equals("DIVISAO");
    }

}
