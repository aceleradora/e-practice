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
        boolean mensagem;
        String tipoToken = "VARIAVEL";

        if(mensagem = verificaSeOsParentesesEstaoOk()){
            for (int i = 0; i < tokens.size(); i++){
                if(!parenteses(tokens.get(i)) && mensagem == true){
                    if(tipoToken.equals("VARIAVEL")){
                        mensagem = validaSeEhVariavel(tokens.get(i));
                        tipoToken = "OPERADOR";
                    }
                    else {
                        mensagem = validaSeEhOperador(tokens.get(i));
                        tipoToken = "VARIAVEL";
                    }
                }
            }
        }
        return mensagem;
    }

    private boolean parenteses(String token) {
        return tokenID.identifica(token).equals("PARENTESES_ABERTO")
                || tokenID.identifica(token).equals("PARENTESES_FECHADO");
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