package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class GerenciadorSemantico {
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private Validador validador;
    private ArrayList<String> tokens;

    public GerenciadorSemantico(TabelaDeSimbolos tabelaDeSimbolos, Lexer lexer, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao, ValidadorDeAtribuicao validadorDeAtribuicao) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.lexer = lexer;
        this.validadorDeDeclaracao = validadorDeDeclaracao;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        identificadorDeToken = new IdentificadorDeToken();
        tokens = new ArrayList<String>();
    }

    public void interpreta(String sentenca) {
        tokens = lexer.tokenizar(sentenca);
        selecionaValidadorAdequado();
        validador.valida(tokens);
    }

    private void selecionaValidadorAdequado() {
        if (primeiroTokenIdentificado().equals("PALAVRA_RESERVADA")){
            validador = validadorDeDeclaracao;
        } else if (primeiroTokenIdentificado().equals("IDV")) {
            validador = validadorDeAtribuicao;
        }
    }

    private String primeiroTokenIdentificado() {
        return identificadorDeToken.identifica(tokens.get(0));
    }
}