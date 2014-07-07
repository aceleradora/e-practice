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
        ArrayList<String> tokens = lexer.tokenizar(frase);

        int quantidadeDeErros = 0;
        for(int i = 0; i < tokens.size(); i++){
            if(tokenizer.identifica(tokens.get(i)) == "INVALIDO") quantidadeDeErros++;
       }

        if(quantidadeDeErros <= 0) return "Código correto!";
        else return "Erro de Sintaxe!";


    }
}
