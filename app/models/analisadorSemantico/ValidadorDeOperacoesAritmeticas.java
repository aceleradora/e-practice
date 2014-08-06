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
        return true;
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