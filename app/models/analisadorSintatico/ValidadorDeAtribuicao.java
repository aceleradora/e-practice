package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeAtribuicao {
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeTokens;

    public ValidadorDeAtribuicao(Lexer lexer, IdentificadorDeToken identificadorDeTokens) {
        this.lexer = lexer;
        this.identificadorDeTokens = identificadorDeTokens;
    }

    public boolean validaPrimeiroToken(String primeiroToken) {

        String token = identificadorDeTokens.identifica(primeiroToken);
        if(token == "IDV")
            return true;
        else
            return false;
    }

    public boolean validaSegundoToken(String segundoToken){
        String token = identificadorDeTokens.identifica(segundoToken);
        if(token == "IGUAL")
            return true;
        else
            return false;
    }
}
