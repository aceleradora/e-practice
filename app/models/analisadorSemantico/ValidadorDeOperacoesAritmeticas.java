package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.arvore.PostFix;
import models.calculadora.CalculadoraDeResultado;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeOperacoesAritmeticas implements Validador{

    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ArrayList<String> listaDetokens;
    String tokenInvalido;
    int tipoDeErro;
    private ArrayList<String> tokensParaPosFixar;
    private PostFix postFix;
    private CalculadoraDeResultado calculadoraDeResultado;

    public ValidadorDeOperacoesAritmeticas(TabelaDeSimbolos tabela) {
        identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabela;
        postFix = new PostFix();
        tokensParaPosFixar = new ArrayList<>();
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

    private int calculaValorDaExpressaoAritmetica() {
        return calculadoraDeResultado.calculaOperacaoAPartirDoPostFix(postFix.criaPosfix(tokensParaPosFixar));
    }

    private void copiaTokensDaExpressaoASerResolvida() {
        for (int i = 2; i < listaDetokens.size(); i++) {
            tokensParaPosFixar.add(listaDetokens.get(i));
        }
    }

    @Override
    public String retornaMensagemErro() {
        if (tipoDeErro == 1)
            return "A variável " + tokenInvalido + " não foi declarada.";
        if (tipoDeErro == 2)
            return "A variável " + tokenInvalido + " não é do tipo Inteiro.";
        return "";
    }
}