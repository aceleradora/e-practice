package models.resolveOperacoes;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResolveOperacoesDeConcatenacao {
    ArrayList<String> tokens;
    IdentificadorDeToken identificadorDeToken;
    TabelaDeSimbolos tabelaDeSimbolos;

    public ResolveOperacoesDeConcatenacao() {
        tokens = new ArrayList<String>();
        identificadorDeToken = new IdentificadorDeToken();
        tabelaDeSimbolos = new TabelaDeSimbolos();
    }

    public String concatenaStrings(ArrayList<String> tokens) {
        String palavra = "";

        for (int i = 0; i < tokens.size(); i++){
            if (tabelaDeSimbolos.simboloExiste(tokens.get(i))){
                palavra += tabelaDeSimbolos.getValor(tokens.get(i));
            }
            if (!identificadorDeToken.identifica(tokens.get(i)).equalsIgnoreCase("CONCATENACAO")){
                palavra += tokens.get(i);
            }
        }
        return palavra;
    }
}
