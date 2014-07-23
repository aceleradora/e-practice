package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeAtribuicao implements Validador {

    private IdentificadorDeToken identificadorDeTokens;
    private ArrayList<String> tokens;

    public ValidadorDeAtribuicao(IdentificadorDeToken identificadorDeTokens) {
        this.identificadorDeTokens = identificadorDeTokens;
    }

    public boolean validaPrimeiroToken() {
        String token = identificadorDeTokens.identifica(tokens.get(0));
        if(token == "IDV")
            return true;
        else
            return false;
    }

    public String mensagemDeErroNoPrimeiroToken(){
        String retorno = "";
        if (!(validaPrimeiroToken())){
            retorno =  "Nome de variável incorreto. \n";
        }
        return retorno;
    }

    public boolean validaSegundoToken(){
        String token = identificadorDeTokens.identifica(tokens.get(1));
        if(token.equals("IGUAL"))
            return true;
        else
            return false;
    }

    public String mensagemDeErroNoSegundoToken(){
        String retorno = "";
        if (!(validaSegundoToken())){
            retorno =  "Esperava \"=\" para atribuição. \n";
        }
        return retorno;
    }

    public boolean validaTerceiroToken() {
        String token = identificadorDeTokens.identifica(tokens.get(2));
        if(token == "NUMERO" || token == "IDV" || token == "CONSTANTE_TIPO_STRING")
            return true;
        else
            return false;
    }

    public String mensagemDeErroNoTerceiroToken(){
        String retorno = "";
        if (!(validaTerceiroToken())){
            retorno =  "Esperava uma variavel ou um valor numérico ou uma String. \n";
        }
        return retorno;
    }

    @Override
    public boolean valida(ArrayList<String> tokens){
        boolean retorno = false;
        this.tokens = tokens;
        retorno =
                validaPrimeiroToken() &&
                validaSegundoToken() &&
                validaTerceiroToken();

        return retorno;
    }

    @Override
    public String retornaMensagemErro(){
        String mensagem = "";
        mensagem = mensagemDeErroNoPrimeiroToken() + mensagemDeErroNoSegundoToken()
                + mensagemDeErroNoTerceiroToken();
        return mensagem;
    }
}