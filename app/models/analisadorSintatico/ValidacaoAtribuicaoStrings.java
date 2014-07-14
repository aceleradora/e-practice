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
        if (getTokenIdentificado(0).equals("IDV")){
            return "O primeiro token é uma variável.";
        } else {
            return "Você digitou "+ tokens.get(0) +" e deveria ser uma variável.";
        }
    }

    public String validaSegundoToken() {
        if (getTokenIdentificado(1).equals("IGUAL")){
            return "O segundo token é uma atribuição.";
        } else {
            return "Você digitou \""+ tokens.get(1) +"\" e deveria ser uma atribuição.";
        }
    }

    public String validaTerceiroToken() {
        if (getTokenIdentificado(2).equals("IDV")|| getTokenIdentificado(2).equals("CONSTANTE_TIPO_STRING")){
            return "O terceiro token é válido.";
        } else {
            return "Você digitou \""+ tokens.get(2) +"\" e deveria ser uma variável ou uma constante do tipo String.";
        }
    }

    private String getTokenIdentificado(int indice) {
        return identificador.identifica(tokens.get(indice));
    }

    public int quantosTokensTemDepoisDoIgual() {
        return (tokens.size() - 2);
    }

    public String validaTokensDepoisDoIgual() {

        if(quantosTokensTemDepoisDoIgual()>1){
            for (int i = 2; i<tokens.size(); i++){
                boolean indexPar = i%2==0;
                boolean variavel = getTokenIdentificado(i).equals("IDV");
                boolean constanteString = getTokenIdentificado(i).equals("CONSTANTE_TIPO_STRING");
                boolean simboloConcatenacao = getTokenIdentificado(i).equals("CONCATENACAO");

                if((indexPar) && (!((variavel)||(constanteString)))) {
                    return "Você digitou \""+ tokens.get(i) + "\" e deveria ser uma variável ou constante String.";
                }
                if((!indexPar) && (!simboloConcatenacao)) {
                    return "Você digitou \"" + tokens.get(i) + "\" e deveria ser \"<>\".";
                }
            }

        } else {
            return validaTerceiroToken();
        }
        return "A concatenação foi feita corretamente.";
    }

    public ArrayList<String> getMensagens() {
        ArrayList<String> mensagens = new ArrayList<String>();

        if(!validaPrimeiroToken().equals("O primeiro token é uma variável.")){
            mensagens.add(validaPrimeiroToken());
        }
        if(!validaSegundoToken().equals("O segundo token é uma atribuição.")){
            mensagens.add(validaSegundoToken());
        }
        if((!validaTerceiroToken().equals("O terceiro token é válido.")) && quantosTokensTemDepoisDoIgual() == 1){
           mensagens.add(validaTerceiroToken());
        }
        if(!validaTokensDepoisDoIgual().equals("A concatenação foi feita corretamente.")){
            mensagens.add(validaTokensDepoisDoIgual());
        }
        return mensagens;
    }

    public boolean valida() {
        boolean retorno = false;
        if(getMensagens().isEmpty()){
            retorno = true;
        }
        return retorno;
    }
}