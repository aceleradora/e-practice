package models.Arvore;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class ShuntingYard {
    public Fila<String> fila;
    //public Pilha
    IdentificadorDeToken identificadorDeToken;
    TabelaDeSimbolos tabelaDeSimbolos;

    public ShuntingYard(IdentificadorDeToken identificadorDeToken, TabelaDeSimbolos tabelaDeSimbolos) {
        this.fila = new Fila<String>();
        this.identificadorDeToken = identificadorDeToken;
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean adicionaTokenSeForInteiro(String token) {
        if(verificaSeEhInteiro(token)){
            fila.adicionaItem(token);
            return true;
        }
        return false;
    }

    public boolean verificaSeEhInteiro(String token){
        return identificadorDeToken.identifica(token) == "NUMERO" ||
                (tabelaDeSimbolos.simboloExiste(token) &&
                        tabelaDeSimbolos.getTipoSimbolo(token)=="Inteiro");
    }

    public boolean adicionaAteAcharOperador(ArrayList<String> tokens) {
        boolean retorno = false;
        for (String token : tokens) {
            if(!verificaSeEhOperador(token)){
                adicionaTokenSeForInteiro(token);
            }else{
                return true;
            }
        }
        return retorno;
    }

    private boolean verificaSeEhOperador(String token) {
        return identificadorDeToken.identifica(token) == "ADICAO" ||
                identificadorDeToken.identifica(token) == "SUBTRACAO" ||
                identificadorDeToken.identifica(token) == "MULTIPLICACAO" ||
                identificadorDeToken.identifica(token) == "DIVISAO";
    }

}
