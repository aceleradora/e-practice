package models.analisadorSemantico;

import models.TabelaDeSimbolos;
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


}
