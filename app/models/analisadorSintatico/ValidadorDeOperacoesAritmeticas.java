package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas {

    private IdentificadorDeToken tokenID;
    private ArrayList<String> tokens;

    public ValidadorDeOperacoesAritmeticas(ArrayList<String> tokens) {
        this.tokenID = new IdentificadorDeToken();
        this.tokens = tokens;
    }

    public boolean validaSeEhVariavel(String token){
        if(tokenID.identifica(token).equalsIgnoreCase("NUMERO")||
                tokenID.identifica(token).equalsIgnoreCase("IDV")) {
            return true;
        } else {
            return false;
        }

    }

    public boolean validaSeEhOperador(String token){
        if(tokenID.identifica(token).equalsIgnoreCase("ADICAO")
                    ||
                    tokenID.identifica(token).equalsIgnoreCase("SUBTRACAO")
                    ||
                    tokenID.identifica(token).equalsIgnoreCase("MULTIPLICACAO")
                    ||
                    tokenID.identifica(token).equalsIgnoreCase("DIVISAO")){
                return true;
        }
        else{
            return false;
        }

    }

    public boolean validarOperacoesAritmeticas() {
        boolean mensagem = true;
        String tipoToken = "VARIAVEL";
        int cont = 0;
        boolean parenteses = true;

        if(mensagem = verificaSeOsParentesesEstaoOk()){
            while(cont<=tokens.size()-1){
                parenteses = (tokenID.identifica(tokens.get(cont)).equals("PARENTESES_ABERTO") || tokenID.identifica(tokens.get(cont)).equals("PARENTESES_FECHADO"));
                if(!parenteses && mensagem == true){
                    if(tipoToken.equals("VARIAVEL")){
                        mensagem = validaSeEhVariavel(tokens.get(cont));
                        tipoToken = "OPERADOR";
                    }
                    else {
                        mensagem = validaSeEhOperador(tokens.get(cont));
                        if(mensagem == true && validaSeEhVariavel(tokens.get(cont+1))){
                            tipoToken = "VARIAVEL";
                        } else {
                            mensagem = false;
                        }
                    }
                }
            cont++;
            }
        }
        return mensagem;
    }


    public boolean verificaSeOsParentesesEstaoOk() {
        int contaParentese = 0;
        int contaParenteseAberto = 0;
        int contaParenteseFechado = 0;

        for(int i = 0; i <tokens.size(); i++){
            if(tokens.get(i).equals("(")){
                contaParentese++;
                contaParenteseAberto++;
            }
            if (tokens.get(i).equals(")")){
                if (contaParenteseAberto > 0){
                    contaParenteseFechado++;
                    contaParentese--;
                } else {
                    return false;
                }
            }
        }

        if(contaParentese == 0){
            return true;
        } else {
            return false;
        }
    }

    public int verificaQuantidadeExpressoesComParenteses() {
        int quantidadeDeParenteses = 0;
        if(verificaSeOsParentesesEstaoOk()){
            for(int i = 0; i <tokens.size(); i++){
                if(tokens.get(i).equals("(")){
                    quantidadeDeParenteses++;
                }
            }
        }
        return quantidadeDeParenteses;
    }
}