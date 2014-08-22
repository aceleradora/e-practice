package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class GerenciadorSemantico {
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeConcatenacao validadorDeConcatenacao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacaoAritmetica;
    private ValidadorGenerico validadorGenerico;
    private Validador validador;
    private ArrayList<String> tokens;
    private TabelaDeSimbolos tabelaDeSimbolos;

    public GerenciadorSemantico(ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao, ValidadorDeAtribuicao validadorDeAtribuicao, ValidadorDeConcatenacao validadorDeConcatenacao, ValidadorDeOperacoesAritmeticas validadorDeOperacaoAritmetica, ValidadorGenerico validadorGenerico) {
        this.validadorDeDeclaracao = validadorDeDeclaracao;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        this.validadorDeConcatenacao = validadorDeConcatenacao;
        this.validadorDeOperacaoAritmetica = validadorDeOperacaoAritmetica;
        this.validadorGenerico = validadorGenerico;
        lexer = new Lexer();
        tokens = new ArrayList<String>();
        identificadorDeToken = new IdentificadorDeToken();
    }

    public void interpreta(String sentenca) {
        tokens = lexer.tokenizar(sentenca);
        selecionaValidadorAdequado();
        validador.valida(tokens);
    }

    public String mostraMensagensDeErro(){
        return validador.retornaMensagemErro();
    }

    private void selecionaValidadorAdequado() {
        if (primeiroTokenIdentificado().equals("PALAVRA_RESERVADA")){
            validador = validadorDeDeclaracao;
        } else if (listaDeTokensContemAlgumOperadorAritmetico()) {
            validador = validadorDeOperacaoAritmetica;
        } else if(listaDeTokensContemOperadorDeConcatenacao()) {
            validador = validadorDeConcatenacao;
        } else if (primeiroTokenIdentificado().equals("IDV")) {
            validador = validadorDeAtribuicao;
        } else {
            validador = validadorGenerico;
        }
    }

    private boolean listaDeTokensContemOperadorDeConcatenacao() {
        return tokens.contains("<>");
    }

    private boolean listaDeTokensContemAlgumOperadorAritmetico() {
        return tokens.contains("+") || tokens.contains("-") || tokens.contains("*") || tokens.contains("/");
    }

    private String primeiroTokenIdentificado() {
        return identificadorDeToken.identifica(tokens.get(0));
    }

    public TabelaDeSimbolos getTabelaDeSimbolos() {
        return tabelaDeSimbolos;
    }
}