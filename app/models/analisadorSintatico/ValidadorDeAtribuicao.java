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

    public boolean validaQuantidadeDeTokens() {
        if (tokens.size() <= 4) {
            return true;
        } else {
            return false;
        }
    }

    public String mensagemDeErroParaQuantidadeInvalidaDeTokens() {
        String retorno = "";
        if (!(validaQuantidadeDeTokens()))
            retorno = "Existe(m) erros(s) sintático(s) na atribuição. \n";
        return retorno;
    }

    public boolean validaPrimeiroToken() {
        String token = identificadorDeTokens.identifica(tokens.get(0));
        if (token == "IDV")
            return true;
        else
            return false;
    }

    public String mensagemDeErroNoPrimeiroToken() {
        String retorno = "";
        if (!(validaPrimeiroToken())) {
            retorno = "Nome de variável incorreto. \n";
        }
        return retorno;
    }

    public boolean validaSegundoToken() {
        String token = identificadorDeTokens.identifica(tokens.get(1));
        if (token.equals("IGUAL"))
            return true;
        else
            return false;
    }

    public String mensagemDeErroNoSegundoToken() {
        String retorno = "";
        if (!(validaSegundoToken())) {
            retorno = "Sinal de igual esperado para atribuição. \n";
        }
        return retorno;
    }

    public boolean validaTerceiroToken() {
        String token = identificadorDeTokens.identifica(tokens.get(2));
        if (tokens.size() == 3) {
            if (token == "NUMERO" || token == "IDV" || token == "CONSTANTE_TIPO_STRING")
                return true;
        } else if (tokens.size() == 4) {
            if (token == "ADICAO" || token == "SUBTRACAO") {
                String quartoToken = identificadorDeTokens.identifica(tokens.get(3));
                if (quartoToken == "NUMERO" || quartoToken == "IDV")
                    return true;
            } else
                return false;
        }
        return false;
    }


    public String mensagemDeErroNoTerceiroToken(){
        String retorno = "";
        if (!(validaTerceiroToken())) {
            if (tokens.size() == 3) {
                retorno = "Variável, valor numérico ou uma string são esperados. \n";
            }
                else
                    retorno = "Existe(m) erros(s) sintático(s) na atribuição. \n";
            }


        return retorno;
    }

    @Override
    public boolean valida(ArrayList<String> tokens){
        boolean retorno = false;
        this.tokens = tokens;
        retorno =
                validaQuantidadeDeTokens() &&
                validaPrimeiroToken() &&
                validaSegundoToken() &&
                validaTerceiroToken();

        return retorno;
    }

    @Override
    public String retornaMensagemErro(){
        String mensagem = "";

        if(mensagem == ""){
            mensagem = mensagemDeErroParaQuantidadeInvalidaDeTokens();
        }
        if(mensagem == ""){
            mensagem = mensagemDeErroNoPrimeiroToken();
        }
        if(mensagem == ""){
            mensagem = mensagemDeErroNoSegundoToken();
        }
        if(mensagem == ""){
            mensagem = mensagemDeErroNoTerceiroToken();
        }
        return mensagem;
    }
}