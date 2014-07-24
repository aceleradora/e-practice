package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidadorDeConcatenacao {
    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> listaDetokens;
    String tokenInvalido;
    int tipoDeErro;


    public ValidadorDeConcatenacao(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        for(int i = 0; i < listaDetokens.size(); i++){
            if(listaDetokens.get(i) != "=" && listaDetokens.get(i) != "<>" ) {
                if (!tabelaDeSimbolos.simboloExiste(listaDetokens.get(i))) {

                    tokenInvalido = listaDetokens.get(i);
                    tipoDeErro = 1;
                    return false;
                }
                if(tabelaDeSimbolos.getTipoSimbolo(listaDetokens.get(i)) != "String") {
                    tokenInvalido = listaDetokens.get(i);
                    tipoDeErro = 2;
                    return false;
                }
            }
        }

        return true;
    }

    public String retornaMensagemErro() {


        if(tipoDeErro == 1)
            return "Erro: a variável " + tokenInvalido + " não foi declarada.";
        else
            return "Erro: a variável " + tokenInvalido + " não é do tipo String.";

    }

}
