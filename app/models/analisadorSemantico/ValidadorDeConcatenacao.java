package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.resolveOperacoes.ResolveOperacoesDeConcatenacao;

import java.util.ArrayList;

public class ValidadorDeConcatenacao implements Validador{

    private TabelaDeSimbolos tabelaDeSimbolos;
    private IdentificadorDeToken identificadorDeToken;
    private ResolveOperacoesDeConcatenacao resolveOperacoesDeConcatenacao;
    private ArrayList<String> listaDetokens;
    private ArrayList<String> tokensParaPosFixar;
    private String tokenInvalido;
    private int tipoDeErro;

    public ValidadorDeConcatenacao(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        identificadorDeToken = new IdentificadorDeToken();
        tokensParaPosFixar = new ArrayList<String>();
        resolveOperacoesDeConcatenacao = new ResolveOperacoesDeConcatenacao(tabelaDeSimbolos);
    }

    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        tokensParaPosFixar.clear();

        for (int i = 0; i < listaDetokens.size(); i++){

                if (identificadorDeToken.identifica(listaDetokens.get(i)).equals("CONSTANTE_TIPO_STRING")){
                    i++;
                }
                else if ((identificadorDeToken.identifica(listaDetokens.get(i)).equals("IDV"))) {
                    if(!tabelaDeSimbolos.simboloExiste(listaDetokens.get(i))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 1;
                        return false;
                    }
                    if (!(tabelaDeSimbolos.getTipoSimbolo(listaDetokens.get(i)).equals("String"))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 2;
                        return false;
                    }
                }
            }

        copiaTokensDaExpressaoASerResolvida();
        String resultado = resolveConcatenacoesDeString();
        tabelaDeSimbolos.atualizaValor(listaDetokens.get(0), String.valueOf(resultado));
        return true;
    }

    public String retornaMensagemErro() {
        if (tipoDeErro == 1)
            return "A variável " + tokenInvalido + " não foi declarada.";
        if (tipoDeErro == 2)
            return "A variável " + tokenInvalido + " não é do tipo String.";
        return "";
    }

    private String resolveConcatenacoesDeString() {
        return resolveOperacoesDeConcatenacao.concatenaStrings(tokensParaPosFixar);
    }

    private void copiaTokensDaExpressaoASerResolvida() {
        for (int i = 2; i < listaDetokens.size(); i++) {
            tokensParaPosFixar.add(listaDetokens.get(i));
        }
    }
}
