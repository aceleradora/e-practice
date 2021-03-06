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
        tokensParaPosFixar.clear();
        for (int i = 0; i < listaDetokens.size(); i++){

                if (tokenEhUmaVariavel(i)) {
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

    private boolean tokenEhUmaVariavel(int i) {
        return (identificadorDeToken.identifica(listaDetokens.get(i)).equals("IDV"));
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
        converteIdvsEmValores();
        
        divideTokensEmTokensComUnarios();
        for (int i = 2; i < listaDetokens.size(); i++) {
            tokensParaPosFixar.add(listaDetokens.get(i));
        }
    }

    private void converteIdvsEmValores() {
        for (int i = 2; i < listaDetokens.size(); i++) {
            if(tokenEhUmaVariavel(i) &&
                    !tabelaDeSimbolos.getVariaveisDeResultado().contains(listaDetokens.get(i))){
                listaDetokens.set(i, tabelaDeSimbolos.getValor(listaDetokens.get(i)));
            }
        }
    }

    public void divideTokensEmTokensComUnarios(){
        ArrayList<String> novosTokens = new ArrayList<String>();
        String aux = "";
        for (int i = 0; i < listaDetokens.size(); i++){
            if (listaDetokens.get(i).equals("+") || listaDetokens.get(i).equals("-")){
                if(verificaTokenAnterior(i) && verificaSeProximoTokenEhNumero(i)){
                    if(listaDetokens.get(i).equals("+")){
                        aux = listaDetokens.get(i+1);
                    } else {
                        if(listaDetokens.get(i+1).charAt(0)=='-'){
                            aux = listaDetokens.get(i+1).substring(1);
                        } else if (listaDetokens.get(i+1).charAt(0)=='+'){
                            aux = listaDetokens.get(i) + listaDetokens.get(i+1).substring(1);
                        } else {
                            aux = listaDetokens.get(i) + listaDetokens.get(i+1);
                        }
                    }
                    novosTokens.add(aux);
                    i++;
                }
                else if(verificaTokenAnterior(i) && verificaSeProximoTokenEhParenteseAberto(i)){
                    if(listaDetokens.get(i).equals("+")){
                        aux = "1";
                    } else {
                        aux = listaDetokens.get(i) + "1";
                    }
                    novosTokens.add(aux);
                    novosTokens.add("*");
                }
                else{
                    novosTokens.add(listaDetokens.get(i));
                }
            }
            else {
                novosTokens.add(listaDetokens.get(i));
            }
        }
        listaDetokens = novosTokens;
    }

    private boolean verificaTokenAnterior(int i){
        boolean verificador = false;
        if(listaDetokens.get(i-1) == null){
            return verificador;
        }
        if (listaDetokens.get(i-1).equals("=")
                || listaDetokens.get(i-1).equals("+")
                || listaDetokens.get(i-1).equals("-")
                || listaDetokens.get(i-1).equals("*")
                || listaDetokens.get(i-1).equals("/")
                || listaDetokens.get(i-1).equals("(")){
            verificador = true;
        }
        return verificador;
    }

    private boolean verificaSeProximoTokenEhNumero(int i){
        boolean verificador = false;
        if(listaDetokens.get(i+1) == null){
            return verificador;
        }
        if(listaDetokens.get(i+1).length()>1){
            if(listaDetokens.get(i+1).charAt(0)=='-' ||
                    listaDetokens.get(i+1).charAt(0)=='+'){
                if(Character.isDigit(listaDetokens.get(i+1).charAt(1))){
                    verificador = true;
                }
            }
        }
        if (Character.isDigit(listaDetokens.get(i + 1).charAt(0))){
            verificador = true;
        }
        return verificador;
    }

    private boolean verificaSeProximoTokenEhParenteseAberto(int i){
        boolean verificador = false;
        if(listaDetokens.get(i+1) == null){
            return verificador;
        }
        else if (listaDetokens.get(i + 1).equals("(")){
            verificador = true;
        }
        return verificador;
    }
}