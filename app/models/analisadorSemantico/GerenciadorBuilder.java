package models.analisadorSemantico;

public class GerenciadorBuilder {
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;

    public GerenciadorBuilder com(ValidadorDeAtribuicao validadorDeAtribuicao) {
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel) {
        this.validadorDeDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel;
        return this;
    }

    public GerenciadorSemantico geraGerenciador() {
        return new GerenciadorSemantico(validadorDeDeclaracaoDeVariavel, validadorDeAtribuicao);
    }
}
