package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;

import java.util.ArrayList;

/**
 * Created by alunos4 on 21/07/14.
 */
public class ValidadorDeDeclaracaoDeVariavel implements Validador {

    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> tokens;



    public ValidadorDeDeclaracaoDeVariavel(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public void adicionarParaTabelaDeSimbolos() {
        tabelaDeSimbolos.adicionaSimbolo(tokens.get(1), tokens.get(3));

    }


    @Override
    public boolean valida(ArrayList<String> tokens){
        this.tokens = tokens;
        if(tabelaDeSimbolos.simboloExiste(tokens.get(1))){
            return false;
        }
        else {
            adicionarParaTabelaDeSimbolos();
            return true;
        }

    }

    @Override
    public String retornaMensagemErro() {
        String erros = "";
        if (!valida(tokens))
            erros = "A variável "+tokens.get(1)+" ja foi declarada.";

        return erros;
    }

}

