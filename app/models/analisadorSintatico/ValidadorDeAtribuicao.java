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
            retorno =  "Nome de variável incorreto.";
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

<<<<<<< HEAD
    public String mensagemDeErroNoSegundoToken(String frase){
=======
    public String mensagemDeErroNoSegundoToken(){
>>>>>>> a8933007923f7d5e5ba1eca502d59f484af1ca2d
        String retorno = "";
        if (!(validaSegundoToken())){
            retorno =  "Esperava \"=\" para atribuição.";
        }
        return retorno;
    }

<<<<<<< HEAD
    public boolean validaTerceiroToken(String terceiroToken) {

        String token = identificadorDeTokens.identifica(terceiroToken);
        if(token == "NUMERO")
=======
    public boolean validaTerceiroToken() {
        String token = identificadorDeTokens.identifica(tokens.get(2));
        if(token == "NUMERO" || token == "IDV" || token == "CONSTANTE_STRING")
>>>>>>> a8933007923f7d5e5ba1eca502d59f484af1ca2d
            return true;
        else
            return false;
    }

<<<<<<< HEAD
    public String mensagemDeErroNoTerceiroToken(String frase){
=======
    public String mensagemDeErroNoTerceiroToken(){
>>>>>>> a8933007923f7d5e5ba1eca502d59f484af1ca2d
        String retorno = "";
        if (!(validaTerceiroToken())){
            retorno =  "Esperava uma variavel ou um valor numérico ou uma String.";
        }
        return retorno;
    }

<<<<<<< HEAD
    public String mensagemDeErro(String frase){
        String mensagem = "";
        mensagem = mensagemDeErroNoPrimeiroToken(frase) +"\n"+ mensagemDeErroNoSegundoToken(frase)
                +"\n"+ mensagemDeErroNoTerceiroToken(frase);
        return mensagem;

    }

    private ArrayList<String> converteStringParaArray(String frase) {
        tokens = lexer.tokenizar(frase);
        return tokens;
    }

    public boolean validaIdv(String frase) {
       boolean retorno = false;
       tokens = converteStringParaArray(frase);
       retorno = tabelaDeSimbolos.simboloExiste(tokens.get(0))
               && tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(0), "Inteiro");
       return retorno;

    }

    public boolean valida(String frase){
=======
    @Override
    public boolean valida(ArrayList<String> tokens){
>>>>>>> a8933007923f7d5e5ba1eca502d59f484af1ca2d
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
        mensagem = mensagemDeErroNoPrimeiroToken() +"\n"+ mensagemDeErroNoSegundoToken()
                +"\n"+ mensagemDeErroNoTerceiroToken();
        return mensagem;
    }
}