package models.Arvore;

import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by alunos4 on 12/08/14.
 */
public class PostFix {
    IdentificadorDeToken identificadorDeToken;
    Stack<String> pilha = new Stack<String>();
    Queue<Character> fila;
    String posfix = "";

    public PostFix(){
        identificadorDeToken = new IdentificadorDeToken();
    }

    public String criaPosfix(ArrayList<String> tokens) {
        for(int i = 0; i < tokens.size(); i++) {

            if(identificadorDeToken.identifica(tokens.get(i)).equals("NUMERO") ||
                identificadorDeToken.identifica(tokens.get(i)).equals("IDV")) {
                    posfix += tokens.get(i);
            }

            else if(!pilha.empty()) {
                if(identificadorDeToken.identifica(pilha.peek()).equals("MULTIPLICACAO") ||
                    identificadorDeToken.identifica(pilha.peek()).equals("DIVISAO")) {
                    posfix += pilha.pop();
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
            else if(identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_ABERTO")){
                pilha.push(tokens.get(i));
            }
            else if(identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_FECHADO")) {
                while (!identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_ABERTO")){
                    if(!identificadorDeToken.identifica(tokens.get(i)).equals("PARENTESES_ABERTO"))
                        posfix += pilha.pop();
                }
                if(identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_ABERTO")){
                    pilha.pop();
                }
            }
        }

        while(!pilha.empty()) {
            if(identificadorDeToken.identifica(pilha.peek()).equals("PARENTESES_FECHADO")){
                pilha.pop();
            }
            posfix += pilha.pop();
        }

        return posfix;
        }

}
