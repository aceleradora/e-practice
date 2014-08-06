package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;

import java.util.ArrayList;

public class ValidadorDeConcatenacao implements Validador{

    TabelaDeSimbolos tabelaDeSimbolos;
    IdentificadorDeToken identificadorDeToken;
    ArrayList<String> listaDetokens;
    String tokenInvalido;
    int tipoDeErro;

    public ValidadorDeConcatenacao(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        identificadorDeToken = new IdentificadorDeToken();
    }

    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        for (int i = 0; i < listaDetokens.size(); i++){
            boolean indexPar = i%2==0;
            if(indexPar) {
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
                else {
                    if (!(listaDetokens.get(i).equals("=") || listaDetokens.get(i).equals("<>"))) {
                        tokenInvalido = listaDetokens.get(i);
                        tipoDeErro = 3;
                        return false;
                    }
                }
            }

        return true;
    }

    public String retornaMensagemErro() {

        if (tipoDeErro == 1)
            return "A variável " + tokenInvalido + " não foi declarada.";
        if (tipoDeErro == 2)
            return "A variável " + tokenInvalido + " não é do tipo String.";
        if (tipoDeErro == 3)
            return "Você digitou " + tokenInvalido + " e deveria ser <>.";
        return "";
    }

}
