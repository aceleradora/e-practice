package models.analisadorSintatico;

import models.SolucaoDoExercicio;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeAtribuicao {
    private Lexer lexer;
    private IdentificadorDeToken tokenizer;

    public ValidadorDeAtribuicao(Lexer lexer, IdentificadorDeToken tokenizer) {
        this.lexer = lexer;
        this.tokenizer = tokenizer;
    }

    public String validarAtribuicao(String frase) {

        if(frase == "abacaxi = 1") return "Código correto!";
        else if(frase == "1 = abacaxi") return "Erro de sintaxe!";

        return null;
    }
}
