package models.analisadorSemantico;

public class GerenciadorBuilder {
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeConcatenacao validadorDeConcatenacao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacaoAritmetica;

    public GerenciadorBuilder com(ValidadorDeAtribuicao validadorDeAtribuicao) {
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel) {
        this.validadorDeDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeConcatenacao validadorDeConcatenacao) {
        this.validadorDeConcatenacao = validadorDeConcatenacao;
        return this;
    }

    public GerenciadorBuilder com(ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas) {
        this.validadorDeOperacaoAritmetica = validadorDeOperacoesAritmeticas;
        return this;
    }

    public GerenciadorSemantico geraGerenciador() {
        return new GerenciadorSemantico(validadorDeDeclaracaoDeVariavel, validadorDeAtribuicao,
                validadorDeConcatenacao, validadorDeOperacaoAritmetica);
    }
}
