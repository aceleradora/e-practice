package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import scala.util.parsing.combinator.testing.Str;

import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas {

    private IdentificadorDeToken tokenID;
    private ArrayList<String> tokens;

    public ValidadorDeOperacoesAritmeticas(ArrayList<String> tokens) {
        this.tokenID = new IdentificadorDeToken();
        this.tokens = tokens;
    }

    public String validarOperacoesAritmeticasSemInsercaoDeParenteses() {
        boolean condicao = true;
        String mensagem = "VALIDO";
        int cont = 0;
        String tipoToken = "VARIAVEL";

        if(verificaSePrimeiroParenteseEncontradoEhUmParenteseAberto().equalsIgnoreCase("NAO_CONTEM")){
            return "INVALIDO";
        }

        while(condicao == true && cont<=tokens.size()-1){
            condicao = false;

                if(tipoToken.equals("VARIAVEL")){
                    if(tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("NUMERO")||
                        tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("IDV")||
                        tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("PARENTESES_ABERTO")||
                        tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("PARENTESES_FECHADO"))
                    {


                        condicao = true;
                        tipoToken = "OPERADOR";
                        cont ++;

                    } else {
                        mensagem = "INVALIDO";
                    }
                }

                else {
                    if (cont!=tokens.size()-1){
                        if(tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("ADICAO")
                                ||
                                tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("SUBTRACAO")
                                ||
                                tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("MULTIPLICACAO")
                                ||
                                tokenID.identifica(tokens.get(cont)).equalsIgnoreCase("DIVISAO")){

                            condicao = true;
                            tipoToken = "VARIAVEL";
                            cont ++;

                        }
                        else mensagem = "INVALIDO";
                    }

                    else mensagem = "INVALIDO";
                }
            }

        return mensagem;

    }

    public String verificaSeOperacaoContemUmParenteseAberto() {
        String mensagem = "";
        for(int i = 0; i < tokens.size(); i++){
            if(tokens.contains("(")){
                mensagem = "CONTEM";
            } else {
                mensagem = "NAO_CONTEM";
            }
        }
        return mensagem;
    }

    public String verificaSeOperacaoContemUmParenteseFechado() {
        String mensagem = "";
        for(int i = 0; i < tokens.size(); i++){
            if(tokens.contains(")")){
                mensagem = "CONTEM";
            } else {
                mensagem = "NAO_CONTEM";
            }
        }
        return mensagem;
    }

    public String verificaSeOperacaoContemUmParenteseAbertoEFechado() {
        String mensagem = "";
        if(verificaSeOperacaoContemUmParenteseAberto().equals("CONTEM") && verificaSeOperacaoContemUmParenteseFechado().equals("CONTEM")){
            mensagem = "CONTEM";
        } else {
            mensagem = "NAO_CONTEM";
        }
        return mensagem;

    }

    public String verificaSePrimeiroParenteseEncontradoEhUmParenteseAberto() {
        String mensagem = "";
        int parenteseAberto = 0;
        int parenteseFechado = 0;
        if(verificaSeOperacaoContemUmParenteseAbertoEFechado().equals("CONTEM")){
            for(int i = 0; i <tokens.size(); i++){
                if(tokens.get(i).equals("(")){
                    parenteseAberto = i;
                }
                if (tokens.get(i).equals(")")){
                    parenteseFechado = i;
                }
            }
            if(parenteseAberto < parenteseFechado){
                mensagem = "VALIDO";
            } else {
                mensagem = "INVALIDO";
            }
        }
        return mensagem;
    }

    public String verificaSeHaConteudoDentroDoParenteses() {
        String mensagem = "";
        int parenteseAberto = 0;
        int parenteseFechado = 0;
        if(verificaSePrimeiroParenteseEncontradoEhUmParenteseAberto().equals("VALIDO")){
            for(int i = 0; i <tokens.size(); i++){
                if(tokens.get(i).equals("(")){
                    parenteseAberto = i;
                }
                if (tokens.get(i).equals(")")){
                    parenteseFechado = i;
                }
            }
            if((parenteseFechado - parenteseAberto) > 1 && !tokens.get(parenteseAberto+1).equals(" ")){
                mensagem = "CONTEM";
            } else {
                mensagem = "NAO_CONTEM";
            }
        }
        return mensagem;
    }

    public String verificaSeOperacaoContemAMesmaQuandidadeDeParentesesAbertoEFechado() {
        String mensagem = "";
        int contaParentese = 0;

        for(int i = 0; i <tokens.size(); i++){
            if(tokens.get(i).equals("(")){
                contaParentese++;
            }
            if (tokens.get(i).equals(")")){
                contaParentese--;
            }
        }

        if(contaParentese == 0){
            mensagem = "CONTEM_MESMA_QUANTIDADE";
        } else {
            mensagem = "NAO_CONTEM_MESMA_QUANTIDADE";
        }

        return mensagem;
    }

}