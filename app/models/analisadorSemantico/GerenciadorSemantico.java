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
    private ValidadorDeConcatenacao validadorDeConcatenacao;
    private Validador validador;
    private ArrayList<String> tokens;

    public GerenciadorSemantico(ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao, ValidadorDeAtribuicao validadorDeAtribuicao, ValidadorDeConcatenacao validadorDeConcatenacao) {
        this.validadorDeDeclaracao = validadorDeDeclaracao;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        this.validadorDeConcatenacao = validadorDeConcatenacao;
        lexer = new Lexer();
        tokens = new ArrayList<String>();
        tabelaDeSimbolos = new TabelaDeSimbolos();
        identificadorDeToken = new IdentificadorDeToken();
    }

    public void interpreta(String sentenca) {
        tokens = lexer.tokenizar(sentenca);
        selecionaValidadorAdequado();
        validador.valida(tokens);
    }

    private void selecionaValidadorAdequado() {
        if (primeiroTokenIdentificado().equals("PALAVRA_RESERVADA")){
            validador = validadorDeDeclaracao;
        } else if(tokens.contains("<>")) {
            validador = validadorDeConcatenacao;
        } else if (primeiroTokenIdentificado().equals("IDV")) {
            validador = validadorDeAtribuicao;
        }
    }

    private String primeiroTokenIdentificado() {
        return identificadorDeToken.identifica(tokens.get(0));
    }
}