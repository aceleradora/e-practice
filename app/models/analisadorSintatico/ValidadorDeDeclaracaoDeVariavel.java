package models.analisadorSintatico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeDeclaracaoDeVariavel implements Validador {

    IdentificadorDeToken identificadorDeToken;
    ArrayList<String> tokens;
    TabelaDeSimbolos tabelaDeSimbolos;

    public ValidadorDeDeclaracaoDeVariavel(TabelaDeSimbolos tabelaDeSimbolos) {
        this.identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabelaDeSimbolos;
    }

    private boolean validaSePrimeiroTokenEVar() {
        if(identificadorDeToken.identifica(tokens.get(0)).equals("PALAVRA_RESERVADA") || identificadorDeToken.identifica(tokens.get(2)).equals(":")){
            return true;
        }
        else return false;
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
            mensagem = "A declaração espera VAR \"IDENTIFICADOR\" : TIPO ou VARRES \"IDENTIFICADOR\" : TIPO, um parametro faltando. \n";
        } else if (verificaSeTokensTemQuantidadeAcimaDoEsperado()) {
            mensagem = "A declaração espera apenas VAR \"IDENTIFICADOR\" : TIPO ou VARRES \"IDENTIFICADOR\" : TIPO. \n";
        } else if(!validaSePrimeiroTokenEVar()){
            mensagem = "A primeira palavra deveria ser \"var\" ou \"varres\" para variável de resultado. \n";
        } else if (!validaSeSegundoTokenEIdv()) {
            mensagem = "A segunda palavra deveria ser um identificador de variável válido. \n";
        } else if (!validaSeTerceiroTokenEDoisPontos()) {
            mensagem = "A terceira palavra deveria ser \":\". \n";
        } else if (!validaSeQuartoTokenETipoDeVariavel()) {
            mensagem = "A quarta palavra deveria ser um tipo válido de variável (String ou Inteiro).";
        }
        return mensagem;
    }
}