package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class GerenciadorSemantico {
    private TabelaDeSimbolos tabelaDeSimbolos;
    private Lexer lexer;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    private ArrayList<String> tokens;

    public GerenciadorSemantico(TabelaDeSimbolos tabelaDeSimbolos, Lexer lexer, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.lexer = lexer;
        this.validadorDeDeclaracao = validadorDeDeclaracao;
        tokens = new ArrayList<String>();
    }

    public void interpreta(String sentenca) {
        tokens = lexer.tokenizar(sentenca);
        if (tokens.get(0).equals("var")){
            validadorDeDeclaracao.valida(tokens);
        }
    }
}