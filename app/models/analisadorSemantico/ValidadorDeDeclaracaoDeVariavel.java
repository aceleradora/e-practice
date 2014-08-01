package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;

import java.util.ArrayList;

public class ValidadorDeDeclaracaoDeVariavel implements Validador {

    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> tokens;
    boolean adicionado;

    public ValidadorDeDeclaracaoDeVariavel(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public void adicionarParaTabelaDeSimbolos() {
        tabelaDeSimbolos.adicionaSimbolo(tokens.get(1), tokens.get(3));
    }

    @Override
    public boolean valida(ArrayList<String> tokens){
        this.tokens = tokens;
        adicionado = false;
        if(tabelaDeSimbolos.simboloExiste(tokens.get(1))){
            return false;
        }
        else {
            adicionarParaTabelaDeSimbolos();
            adicionado = true;
            return true;
        }
    }

    @Override
    public String retornaMensagemErro() {
        String erros = "";
        if (!adicionado)
            erros = "A variável "+tokens.get(1)+" ja foi declarada.";
        return erros;
    }

}

