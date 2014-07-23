package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidadorDeConcatenacao {
    TabelaDeSimbolos tabelaDeSimbolos;

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


    public boolean valida(ArrayList<String> tokensInvalidos) {
        String primeiraVariavel = tokensInvalidos.get(0);
        String segundaVariavel = tokensInvalidos.get(2);
        String terceiraVariavel = tokensInvalidos.get(4);

        if (!verificaSeVariavelExiste(primeiraVariavel))
            return false;
        else if (!verificaSeVariavelExiste(segundaVariavel))
            return false;
        else if(!verificaSeVariavelExiste(terceiraVariavel))
            return false;
        else
            return true;
    }

    public String retornaMensagemErro() {
        return null;
    }
}
