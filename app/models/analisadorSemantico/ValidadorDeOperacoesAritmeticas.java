package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.arvore.PostFix;
import models.resolveOperacoes.CalculadoraDeResultado;

import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas implements Validador{

    private PostFix postFix;
    private ArrayList<String> listaDetokens;
    private ArrayList<String> tokensParaPosFixar;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private IdentificadorDeToken identificadorDeToken;
    private CalculadoraDeResultado calculadoraDeResultado;
    private String tokenInvalido;
    private int tipoDeErro;

    public ValidadorDeOperacoesAritmeticas(TabelaDeSimbolos tabela) {
        identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabela;
        postFix = new PostFix();
        tokensParaPosFixar = new ArrayList<String>();
        calculadoraDeResultado = new CalculadoraDeResultado(tabelaDeSimbolos);
    }

    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        for (int i = 0; i < listaDetokens.size(); i++){

                if ((identificadorDeToken.identifica(listaDetokens.get(i)).equals("IDV"))) {
                    if(!tabelaDeSimbolos.simboloExiste(listaDetokens.get(i))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 1;
                        return false;
                    }
                    if (!(tabelaDeSimbolos.getTipoSimbolo(listaDetokens.get(i)).equals("Inteiro"))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 2;
                        return false;
                    }
                }
            }
        copiaTokensDaExpressaoASerResolvida();
        int resultado = calculaValorDaExpressaoAritmetica();
        tabelaDeSimbolos.atualizaValor(listaDetokens.get(0), String.valueOf(resultado));
        return true;
    }

    public String retornaMensagemErro() {
        if (tipoDeErro == 1)
            return "A variável " + tokenInvalido + " não foi declarada.";
        if (tipoDeErro == 2)
            return "A variável " + tokenInvalido + " não é do tipo Inteiro.";
        return "";
    }

    private int calculaValorDaExpressaoAritmetica() {
        return calculadoraDeResultado.calculaOperacaoAPartirDoPostFix(postFix.criaPosfix(tokensParaPosFixar));
    }

    private void copiaTokensDaExpressaoASerResolvida() {
        for (int i = 2; i < listaDetokens.size(); i++) {
            tokensParaPosFixar.add(listaDetokens.get(i));
        }
    }
}