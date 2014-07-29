package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;
import models.analisadorSintatico.*;

public class MensagemDeFeedback {

    private SolucaoDoExercicio solucaoDoExercicio;
    private GerenciadorDeFeedback gerenciadorDeFeedback;
    private GerenciadorSintatico gerenciadorSintatico;
    private QuebradorDeCodigoEmLinhas quebradorDeCodigoEmLinhas;
    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;

    public MensagemDeFeedback(String codigo) {
        this.lexer = new Lexer();
        this.identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = new TabelaDeSimbolos();
        this.validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
        this.validadorDeAtribuicao = new ValidadorDeAtribuicao(identificadorDeToken);
        this.validadorDeOperacoesAritmeticas = new ValidadorDeOperacoesAritmeticas();
        this.validadorDeConcatenacaoDeStrings = new ValidadorDeConcatenacaoDeStrings();
        this.quebradorDeCodigoEmLinhas = new QuebradorDeCodigoEmLinhas();
        this.gerenciadorSintatico = new GerenciadorSintatico(lexer, identificadorDeToken, validadorDeDeclaracao, validadorDeAtribuicao, validadorDeOperacoesAritmeticas, validadorDeConcatenacaoDeStrings);
        this.gerenciadorDeFeedback = new GerenciadorDeFeedback(codigo, gerenciadorSintatico, quebradorDeCodigoEmLinhas);
        this.solucaoDoExercicio = new SolucaoDoExercicio(codigo);

    }

    public String mostraMensagem(){
        return gerenciadorDeFeedback.pegaFeedback();
    }
}
