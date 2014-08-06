package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeOperacoesAritmeticas implements Validador{

    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabelaDeSimbolos;
    private ArrayList<String> listaDetokens;
    String tokenInvalido;
    int tipoDeErro;

    public ValidadorDeOperacoesAritmeticas(TabelaDeSimbolos tabela) {
        identificadorDeToken = new IdentificadorDeToken();
        this.tabelaDeSimbolos = tabela;
    }

    public boolean valida(ArrayList<String> tokens) {
        this.listaDetokens = tokens;
        for (int i = 0; i < listaDetokens.size(); i++){
            boolean indexPar = i%2==0;
            if(indexPar) {
                if (identificadorDeToken.identifica(listaDetokens.get(i)).equals("NUMERO")){
                    i++;
                }
                else if ((identificadorDeToken.identifica(listaDetokens.get(i)).equals("IDV"))) {
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

            else {
                if (!(listaDetokens.get(i).equals("=")
                        || listaDetokens.get(i).equals("+")
                        || listaDetokens.get(i).equals("-")
                        || listaDetokens.get(i).equals("*")
                        || listaDetokens.get(i).equals("/"))) {
                    tokenInvalido = listaDetokens.get(i);
                    tipoDeErro = 3;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String retornaMensagemErro() {
        if (tipoDeErro == 1)
            return "A variável " + tokenInvalido + " não foi declarada.";
        if (tipoDeErro == 2)
            return "A variável " + tokenInvalido + " não é do tipo Inteiro.";
        if (tipoDeErro == 3)
            return "Você digitou " + tokenInvalido + " e deveria ser operador matemático.";
        return "";
    }
}
