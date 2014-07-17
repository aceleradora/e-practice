package models.analisadorSintatico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeAtribuicao implements Validador{
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeTokens;
    private ArrayList<String> tokens;
    private TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeAtribuicao(Lexer lexer, IdentificadorDeToken identificadorDeTokens) {
        this.lexer = lexer;
        this.identificadorDeTokens = identificadorDeTokens;
    }

    public void setTabelaDeSimbolos(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public TabelaDeSimbolos getTabelaDeSimbolos() {
        return tabelaDeSimbolos;
    }

    public boolean validaPrimeiroToken(String primeiroToken) {

        String token = identificadorDeTokens.identifica(primeiroToken);
        if(token == "IDV")
            return true;
        else
            return false;
    }

    public String mensagemDeErroNoPrimeiroToken(String frase){
        String retorno = "";
        tokens = converteStringParaArray(frase);
        if (!(validaPrimeiroToken(tokens.get(0)))){
            retorno =  "nome de variável não declarado";
        }

        return retorno;
    }

    public boolean validaSegundoToken(String segundoToken){
        String token = identificadorDeTokens.identifica(segundoToken);
        if(token == "IGUAL")
            return true;
        else
            return false;
    }


    public String mensagemDeErroNoSegundoToken(String frase){
        String retorno = "";
        tokens = converteStringParaArray(frase);
        if (!(validaPrimeiroToken(tokens.get(1)))){
            retorno =  "esperava \"=\" para atribuição de inteiros";
        }

        return retorno;
    }



    public boolean validaTerceiroToken(String terceiroToken) {

        String token = identificadorDeTokens.identifica(terceiroToken);
        if(token == "NUMERO")
            return true;
        else
            return false;
    }


    public String mensagemDeErroNoTerceiroToken(String frase){
        String retorno = "";
        tokens = converteStringParaArray(frase);
        if (!(validaTerceiroToken(tokens.get(2)))){
            retorno =  "esperava valores numéricos";
        }

        return retorno;
    }
//    public boolean validaExpressao(String frase) {
//        tokens = converteStringParaArray(frase);
//        boolean retorno = true;
//        for(int i = 2; i < tokens.size(); i++){
//            if(i % 2 == 0 && !identificadorDeTokens.identifica(tokens.get(i)).equals("NUMERO")) retorno = false;
//            if(i % 2 == 1 && !identificadorDeTokens.identifica(tokens.get(i)).equals("SUBTRACAO")) {
//                if (i % 2 == 1 && !identificadorDeTokens.identifica(tokens.get(i)).equals("ADICAO")){
//                    if (i % 2 == 1 && !identificadorDeTokens.identifica(tokens.get(i)).equals("MULTIPLICACAO")){
//                        if (i % 2 == 1 && !identificadorDeTokens.identifica(tokens.get(i)).equals("DIVISAO")){
//                            retorno = false;
//                        }
//                    }
//                }
//            }
//        }
//        return retorno;
//    }


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
        boolean retorno = false;
        tokens = converteStringParaArray(frase);
        retorno =
        validaPrimeiroToken(tokens.get(0)) &&
        validaSegundoToken(tokens.get(1)) &&
        validaTerceiroToken(tokens.get(2)) &&
        validaIdv(frase);

        return retorno;
    }

    @Override
    public boolean valida(ArrayList<String> tokens) {
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        return null;
    }
}