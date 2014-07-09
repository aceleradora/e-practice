package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

/**
 * Created by aluno6 on 07/07/14.
 */
public class ValidadorDeOperacoesAritmeticas {

    private IdentificadorDeToken tokenID;

    public ValidadorDeOperacoesAritmeticas() {
        this.tokenID = new IdentificadorDeToken();
    }

    public String validarArray(ArrayList<String> tokens) {
        boolean condicao = true;
        String msg = "VALIDO";
        int cont = 0;
        String tipoToken = "variavel";

        while(condicao == true && cont<=tokens.size()-1){
            condicao=false;

            if(tipoToken.equals("variavel")){
                if(tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("NUMERO")||
                        tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("IDV")){

                    condicao = true;
                    tipoToken = "operador";
                    cont ++;
                }
                else   msg = "INVALIDO";
            }
            else{
                if (cont!=tokens.size()-1){
                if(tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("ADICAO")
                  ||
                   tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("SUBTRACAO")                               ||
                    tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("MULTIPLICACAO")
                    ||
                     tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("DIVISAO")){

                    condicao = true;
                    tipoToken = "variavel";
                    cont ++;

                }
                else msg = "INVALIDO";
                }


                else msg = "INVALIDO";
                }

        }
        return msg;
    }
}