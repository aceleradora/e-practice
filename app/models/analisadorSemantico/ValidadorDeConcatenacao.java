package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidadorDeConcatenacao {
    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> listaDetokens;


    public ValidadorDeConcatenacao(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public void getTipoDeVariavel(String variavel) {
        tabelaDeSimbolos.getTipoSimbolo(variavel);
    }

    public boolean verificaSeVariavelExiste(String variavel) {
       return tabelaDeSimbolos.simboloExiste(variavel);

    }

    public boolean isString(String variavel) {
        return tabelaDeSimbolos.getTipoSimbolo(variavel)=="String";
    }


    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        for(int i = 0; i < listaDetokens.size(); i++){
            if(tokens.get(i) != "=" && tokens.get(i) != "<>" ) {
                if (!verificaSeVariavelExiste(tokens.get(i)) || !isString(tokens.get(i)))
                    return false;
            }
        }

        return true;
    }

    public String retornaMensagemErro() {


        if(!valida(listaDetokens))
            return "Erro: Variável não declarada.";
        else
            return "Expressão correta!";

    }

}
