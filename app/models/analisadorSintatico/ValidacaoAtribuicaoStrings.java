package models.analisadorSintatico;


import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidacaoAtribuicaoStrings implements Validador{

    ArrayList<String> tokens;
    IdentificadorDeToken identificador;

    public ValidacaoAtribuicaoStrings() {

        identificador = new IdentificadorDeToken();
    }

    public String validaPrimeiroToken() {
        if (comparaToken(0,"IDV")){
            return "O primeiro token é uma variável.";
        } else {
            return "Você digitou "+ tokens.get(0) +" e deveria ser uma variável.";
        }
    }

    public String validaSegundoToken() {
        if (comparaToken(1,"IGUAL")){
            return "O segundo token é uma atribuição.";
        } else {
            return "Você digitou \""+ tokens.get(1) +"\" e deveria ser uma atribuição.";
        }
    }

    public String validaTerceiroToken() {
        if (comparaToken(2,"IDV") || comparaToken(2,"CONSTANTE_TIPO_STRING")){
            return "O terceiro token é válido.";
        } else {
            return "Você digitou \""+ tokens.get(2) +"\" e deveria ser uma variável ou uma constante do tipo String.";
        }
    }

    private boolean comparaToken(int indice, String valor) {
        return getTokenIdentificado(indice).equals(valor);
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
                boolean variavel = comparaToken(i,"IDV");
                boolean constanteString = comparaToken(i,"CONSTANTE_TIPO_STRING");
                boolean simboloConcatenacao = comparaToken(i,"CONCATENACAO");

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


    @Override
    public boolean valida(ArrayList<String> tokens) {
        boolean retorno = false;
        this.tokens = tokens;

        if(retornaMensagemErro() == null || retornaMensagemErro().equals("")){
            retorno = true;
        }
        return retorno;
    }

    @Override
    public String retornaMensagemErro() {

        ArrayList<String> mensagens = new ArrayList<String>();
        String mensagensDeRetorno = "";

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

        for(int i = 0; i < mensagens.size(); i++) {
            mensagensDeRetorno = (i + 1) + ") " + mensagensDeRetorno + "\n";
        }

        return mensagensDeRetorno;
    }
}