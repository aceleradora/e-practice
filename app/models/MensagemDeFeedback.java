package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSemantico.*;
import models.analisadorSintatico.*;

public class MensagemDeFeedback {

    private SolucaoDoExercicio solucaoDoExercicio;
    private GerenciadorDeFeedback gerenciadorDeFeedback;
    private GerenciadorSintatico gerenciadorSintatico;
    private GerenciadorSemantico gerenciadorSemantico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigoEmLinhas;
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private models.analisadorSintatico.ValidadorDeAtribuicao validadorDeAtribuicaoSintatico;
    private models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoSintatico;
    private models.analisadorSintatico.ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSintatico;
    private models.analisadorSintatico.ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoSintatico;

    private models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoSemantico;
    private models.analisadorSemantico.ValidadorDeAtribuicao validadorDeAtribuicaoSemantico;
    private models.analisadorSemantico.ValidadorDeConcatenacao validadorDeConcatenacaoSemantico;
    private models.analisadorSemantico.ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticasSemantico;

    public MensagemDeFeedback(String codigo) {
        this.lexer = new Lexer();
        this.identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = new TabelaDeSimbolos();

        this.validadorDeDeclaracaoSintatico = new models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        this.validadorDeAtribuicaoSintatico = new models.analisadorSintatico.ValidadorDeAtribuicao(identificadorDeToken);
        this.validadorDeOperacoesAritmeticasSintatico = new models.analisadorSintatico.ValidadorDeOperacoesAritmeticas();
        this.validadorDeConcatenacaoSintatico = new models.analisadorSintatico.ValidadorDeConcatenacaoDeStrings();

        this.validadorDeDeclaracaoSemantico = new models.analisadorSemantico.ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        this.validadorDeAtribuicaoSemantico = new models.analisadorSemantico.ValidadorDeAtribuicao(tabelaDeSimbolos);
        this.validadorDeConcatenacaoSemantico = new models.analisadorSemantico.ValidadorDeConcatenacao(tabelaDeSimbolos);
        this.validadorDeOperacoesAritmeticasSemantico = new models.analisadorSemantico.ValidadorDeOperacoesAritmeticas(tabelaDeSimbolos);

        this.quebradorDeCodigoEmLinhas = new QuebradorDeCodigoEmLinhas();
        this.gerenciadorSintatico = new GerenciadorSintatico(lexer, identificadorDeToken, validadorDeDeclaracaoSintatico, validadorDeAtribuicaoSintatico, validadorDeOperacoesAritmeticasSintatico, validadorDeConcatenacaoSintatico);
        this.gerenciadorSemantico = new GerenciadorSemantico(validadorDeDeclaracaoSemantico, validadorDeAtribuicaoSemantico, validadorDeConcatenacaoSemantico, validadorDeOperacoesAritmeticasSemantico);
        this.gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, gerenciadorSemantico, quebradorDeCodigoEmLinhas);
        this.solucaoDoExercicio = new SolucaoDoExercicio(codigo);

    }

    public String mostraMensagem(){
        return gerenciadorDeFeedback.pegaFeedback();
    }
}
