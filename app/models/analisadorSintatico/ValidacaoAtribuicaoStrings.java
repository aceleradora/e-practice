package models.analisadorSintatico;


import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidacaoAtribuicaoStrings {

    ArrayList<String> tokens;
    IdentificadorDeToken identificador;


    public ValidacaoAtribuicaoStrings(ArrayList<String> entradaDoUsuario) {
        tokens = entradaDoUsuario;
        identificador = new IdentificadorDeToken();
    }

    public String validaPrimeiroToken() {
        if (identificador.identifica(tokens.get(0)).equals("IDV")){
            return "O primeiro token é uma variável.";
        } else {
            return "O primeiro token deveria ser uma variável.";
        }
    }

    public String validaSegundoToken() {
        if (identificador.identifica(tokens.get(1)).equals("IGUAL")){
            return "O segundo token é uma atribuição.";
        } else {
            return "O segundo token deveria ser uma atribuição.";
        }
    }
}
