package models.resolveOperacoes;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ResolveOperacoesDeConcatenacao {
    ArrayList<String> tokens;
    IdentificadorDeToken identificadorDeToken;
    TabelaDeSimbolos tabelaDeSimbolos;

    public ResolveOperacoesDeConcatenacao(TabelaDeSimbolos tabelaDeSimbolos) {
        tokens = new ArrayList<String>();
        identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public String concatenaStrings(ArrayList<String> tokens) {
        String palavra = "";
        String aux = "";

        for (int i = 0 ; i < tokens.size(); i++){
            if (tabelaDeSimbolos.simboloExiste(tokens.get(i))){
                palavra += tabelaDeSimbolos.getValor(tokens.get(i));
            } else if (!identificadorDeToken.identifica(tokens.get(i)).equalsIgnoreCase("CONCATENACAO")){
                palavra += tokens.get(i);
            }
        }
        aux = "\"" + palavra.replaceAll("\"", "") + "\"";
        palavra = aux;
        return palavra;
    }
}
