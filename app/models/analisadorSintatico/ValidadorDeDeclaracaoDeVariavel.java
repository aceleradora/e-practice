package models.analisadorSintatico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeDeclaracaoDeVariavel implements Validador{

    IdentificadorDeToken identificadorDeToken;
    ArrayList<String> tokens;
    TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeDeclaracaoDeVariavel(TabelaDeSimbolos tabelaDeSimbolos) {
        this.identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    private boolean validaSePrimeiroTokenEVar() {
        return identificadorDeToken.identifica(tokens.get(0)).equals("PALAVRA_RESERVADA");
    }

    private boolean validaSeSegundoTokenEIdv() {
        return identificadorDeToken.identifica(tokens.get(1)).equals("IDV");
    }

    private boolean validaSeTerceiroTokenEDoisPontos() {
        return identificadorDeToken.identifica(tokens.get(2)).equals("DECLARACAO");
    }

    private boolean validaSeQuartoTokenETipoDeVariavel() {
        return identificadorDeToken.identifica(tokens.get(3)).equals("TIPO_DE_VARIAVEL");
    }

    private boolean verificaSeTokensTemQuantidadeAcimaDoEsperado() {
        return tokens.size() > 4;
    }

    private boolean verificaSeTokensTemQuantidadeAbaixoDoEsperado() {
        return tokens.size() < 4;
    }

    private boolean quantidadeDeTokensForInvalida() {
        return verificaSeTokensTemQuantidadeAcimaDoEsperado() || verificaSeTokensTemQuantidadeAbaixoDoEsperado();
    }

    private boolean tokensSaoValidos() {
        return validaSePrimeiroTokenEVar()
                && validaSeSegundoTokenEIdv()
                && validaSeTerceiroTokenEDoisPontos()
                && validaSeQuartoTokenETipoDeVariavel();
    }

    public void adicionaVariavelNaTabelaDeSimbolos() {
        if(valida(tokens))
            tabelaDeSimbolos.adicionaSimbolo(tokens.get(1), tokens.get(3));
    }

    @Override
    public boolean valida(ArrayList<String> listaDeTokens) {
        tokens = listaDeTokens;

        if(quantidadeDeTokensForInvalida())
            return false;
        return tokensSaoValidos();
    }

    @Override
    public String retornaMensagemErro() {
        String mensagem = "";
        if (verificaSeTokensTemQuantidadeAbaixoDoEsperado()) {
            mensagem = "a declaração espera VAR \"IDENTIFICADOR\" : TIPO, um parametro faltando";
        } else if (verificaSeTokensTemQuantidadeAcimaDoEsperado()) {
            mensagem = "a declaração espera apenas VAR \"IDENTIFICADOR\" : TIPO";
        } else if(!validaSePrimeiroTokenEVar()){
            mensagem = "a primeira palavra deveria ser \"var\" - ";
        } else if (!validaSeSegundoTokenEIdv()) {
            mensagem = "a segunda palavra deveria ser um identificador de variável válido - ";
        } else if (!validaSeTerceiroTokenEDoisPontos()) {
            mensagem = "a terceira palavra deveria ser \":\" - ";
        } else if (!validaSeQuartoTokenETipoDeVariavel()) {
            mensagem = "a quarta palavra deveria ser um tipo válido de variável (string ou inteiro)";
        }
        return mensagem;
    }
}