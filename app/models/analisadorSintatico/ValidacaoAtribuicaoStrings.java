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
            return "Você digitou "+ tokens.get(0) +" e deveria ser uma variável.";
        }
    }

    public String validaSegundoToken() {
        if (identificador.identifica(tokens.get(1)).equals("IGUAL")){
            return "O segundo token é uma atribuição.";
        } else {
            return "Você digitou \""+ tokens.get(1) +"\" e deveria ser uma atribuição.";
        }
    }

    public String validaTerceiroTokens() {
        if (identificador.identifica(tokens.get(2)).equals("IDV")||identificador.identifica(tokens.get(2)).equals("CONSTANTE_TIPO_STRING")){
            return "O terceiro token é válido.";
        } else {
            return "Você digitou \""+ tokens.get(2) +"\" e deveria ser uma variável ou uma constante do tipo String.";
        }
    }

    public int quantosTokensTemDepoisDoIgual() {
        return (tokens.size() - 2);
    }

    public String validaTokensDepoisDoIgual() {

        if(quantosTokensTemDepoisDoIgual()>1){
            for (int i = 2; i<tokens.size(); i++){
                boolean indexPar = i%2==0;
                boolean variavel = identificador.identifica(tokens.get(i)).equals("IDV");
                boolean constanteString = identificador.identifica(tokens.get(i)).equals("CONSTANTE_TIPO_STRING");
                boolean simboloConcatenacao = identificador.identifica(tokens.get(i)).equals("CONCATENACAO");

                if((indexPar) && (!((variavel)||(constanteString)))) {
                    return "Você digitou \""+ tokens.get(i) + "\" e deveria ser uma variável ou constante String.";
                }
                if((!indexPar) && (!simboloConcatenacao)) {
                    return "Você digitou \"" + tokens.get(i) + "\" e deveria ser \"<>\".";
                }
            }

        } else {
            return validaTerceiroTokens();
        }
        return "A concatenação foi feita corretamente.";
    }
}