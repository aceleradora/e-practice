package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidadorDeConcatenacao implements Validador{

    TabelaDeSimbolos tabelaDeSimbolos;
    IdentificadorDeToken identificadorDeToken;
    ArrayList<String> listaDetokens;
    String tokenInvalido;
    int tipoDeErro;

    public ValidadorDeConcatenacao(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        identificadorDeToken = new IdentificadorDeToken();
    }

    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        for (int i = 0; i < listaDetokens.size(); i++){
            if (identificadorDeToken.identifica(listaDetokens.get(i)).equals("IDV")) {
                if (!(listaDetokens.get(i).equals("=") || listaDetokens.get(i).equals("<>"))) {
                    if(!tabelaDeSimbolos.simboloExiste(listaDetokens.get(i))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 1;
                        return false;
                    }
                    if (!(tabelaDeSimbolos.getTipoSimbolo(listaDetokens.get(i)).equals("String"))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 2;
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public String retornaMensagemErro() {
        if (tipoDeErro == 1)
            return "Erro: a variável " + tokenInvalido + " não foi declarada.";
        if (tipoDeErro == 2)
            return "Erro: a variável " + tokenInvalido + " não é do tipo String.";
        return "";
    }

}
