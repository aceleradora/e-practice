package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class GerenciadorSemantico {
    private TabelaDeSimbolos tabelaDeSimbolos;
    private Lexer lexer;
    private ArrayList<String> tokens;

    public GerenciadorSemantico(TabelaDeSimbolos tabelaDeSimbolos, Lexer lexer) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.lexer = lexer;
        tokens = new ArrayList<String>();
    }

    public void interpreta(String sentenca) {
        tokens = lexer.tokenizar(sentenca);
    }
}