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


    public String valida(ArrayList<String> tokensInvalidos) {
        String primeiraVariavel = tokensInvalidos.get(0);
        String segundaVariavel = tokensInvalidos.get(2);

        if (!verificaSeVariavelExiste(primeiraVariavel))
            return "O IDV não foi declarado.";
        else
            return "A variável nome não foi declarada.";
    }
}
