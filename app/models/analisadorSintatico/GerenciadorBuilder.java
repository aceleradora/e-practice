package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

public class GerenciadorBuilder {
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;
    private ValidadorGenerico validadorGenerico;

    public GerenciadorBuilder com(Lexer lexer) {
        this.lexer = lexer;
        return this;
    }


    public GerenciadorBuilder com(IdentificadorDeToken identificadorDeToken) {
        this.identificadorDeToken = identificadorDeToken;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeDeclaracaoDeVariavel validador) {
        this.validadorDeDeclaracaoDeVariavel = validador;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeAtribuicao validador) {
        this.validadorDeAtribuicao = validador;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeOperacoesAritmeticas validador) {
        this.validadorDeOperacoesAritmeticas = validador;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeConcatenacaoDeStrings validador){
        this.validadorDeConcatenacaoDeStrings = validador;
        return this;
    }

    public GerenciadorBuilder com(ValidadorGenerico validador){
        this.validadorGenerico = validador;
        return this;
    }

    public GerenciadorSintatico geraGerenciador() {
        return new GerenciadorSintatico(lexer, identificadorDeToken, validadorDeDeclaracaoDeVariavel,
                validadorDeAtribuicao, validadorDeOperacoesAritmeticas, validadorDeConcatenacaoDeStrings, validadorGenerico);
    }
}