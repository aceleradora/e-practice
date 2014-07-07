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

    public String validarAtribuicao(String frase) {

        ArrayList<String> tokens = lexer.tokenizar(frase);

        ArrayList<String> pilhaDeTokens = new ArrayList<String>();

        for(int i = 0; i < tokens.size(); i++){

            if(identificadorDeTokens.identifica(tokens.get(i)) == "IDV") pilhaDeTokens.add("IDV");
            else if(identificadorDeTokens.identifica(tokens.get(i)) == "IGUAL") pilhaDeTokens.add("IGUAL");
            else if(identificadorDeTokens.identifica(tokens.get(i)) == "NUMERO") pilhaDeTokens.add("NUMERO");

        }

        if(pilhaDeTokens.get(0) != "IDV") return "Erro de sintaxe!";
        else if(pilhaDeTokens.get(1) != "IGUAL") return "Erro de sintaxe!";
        else if(pilhaDeTokens.get(2) != "NUMERO") return "Erro de sintaxe!";
        else return "Código correto!";

        //if(frase == "abacaxi = 1") return "Código correto!";
        //else if(frase == "1 = abacaxi") return "Erro de sintaxe!";
    }
}
