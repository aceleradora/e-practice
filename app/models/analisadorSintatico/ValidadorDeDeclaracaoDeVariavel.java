package models.analisadorSintatico;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidadorDeDeclaracaoDeVariavel {

    IdentificadorDeToken identificadorDeToken;
    ArrayList<String> tokens;
    TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeDeclaracaoDeVariavel(ArrayList<String> tokens, TabelaDeSimbolos tabelaDeSimbolos) {
        this.identificadorDeToken = new IdentificadorDeToken();
        this.tokens = tokens;
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    public boolean validaSePrimeiroTokenEVar() {
        return identificadorDeToken.identifica(tokens.get(0)).equals("PALAVRA_RESERVADA");
    }

    public boolean validaSeSegundoTokenEIdv() {
        return identificadorDeToken.identifica(tokens.get(1)).equals("IDV");
    }

    public boolean validaSeTerceiroTokenEDoisPontos() {
        return identificadorDeToken.identifica(tokens.get(2)).equals("DECLARACAO");
    }

    public boolean validaSeQuartoTokenETipoDeVariavel() {
        return identificadorDeToken.identifica(tokens.get(3)).equals("TIPO_DE_VARIAVEL");
    }

    public boolean verificaSeTokensTemQuantidadeAcimaDoEsperado(ArrayList<String> listaDeTokens) {
        return listaDeTokens.size() > 4;
    }

    public boolean verificaSeTokensTemQuantidadeAbaixoDoEsperado(ArrayList<String> listaDeTokens) {
        return listaDeTokens.size() < 4;
    }

    public boolean validaDeclaracao(ArrayList<String> listaDeTokens) {
        if(quantidadeDeTokensForInvalida(listaDeTokens)){
            return false;
        }
        return tokensSaoValidos();
    }

    private boolean tokensSaoValidos() {
        return validaSePrimeiroTokenEVar()
                        && validaSeSegundoTokenEIdv()
                        && validaSeTerceiroTokenEDoisPontos()
                        && validaSeQuartoTokenETipoDeVariavel();
    }

    private boolean quantidadeDeTokensForInvalida(ArrayList<String> listaDeTokens) {
        return verificaSeTokensTemQuantidadeAcimaDoEsperado(listaDeTokens) || verificaSeTokensTemQuantidadeAbaixoDoEsperado(listaDeTokens);
    }

    public void adicionaVariavelNaTabelaDeSimbolos(ArrayList<String> listaDeTokens) {
        if(this.validaDeclaracao(listaDeTokens)){
            tabelaDeSimbolos.adicionaSimbolo(tokens.get(1), tokens.get(3));
        }
    }

    public String geraMensagensDeErro(ArrayList<String> listaDeTokens) {
        String retorno = "";

        if (verificaSeTokensTemQuantidadeAbaixoDoEsperado(listaDeTokens)) {
            retorno = "a declaração espera VAR \"IDENTIFICADOR\" : TIPO, um parametro faltando";
        } else if (verificaSeTokensTemQuantidadeAcimaDoEsperado(listaDeTokens)) {
            retorno = "a declaração espera apenas VAR \"IDENTIFICADOR\" : TIPO";
        } else if(!validaSePrimeiroTokenEVar()){
            retorno = "a primeira palavra deveria ser \"var\" - ";
        } else if (!validaSeSegundoTokenEIdv()) {
            retorno = "a segunda palavra deveria ser um identificador de variável válido - ";
        } else if (!validaSeTerceiroTokenEDoisPontos()) {
            retorno = "a terceira palavra deveria ser \":\" - ";
        } else if (!validaSeQuartoTokenETipoDeVariavel()) {
            retorno = "a quarta palavra deveria ser um tipo válido de variável (string ou inteiro)";
        }

        return retorno;
    }
}
