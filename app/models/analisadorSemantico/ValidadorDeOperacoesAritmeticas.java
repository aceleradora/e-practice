package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

/**
 * Created by aluno6 on 22/07/14.
 */
public class ValidadorDeOperacoesAritmeticas implements Validador{

    private IdentificadorDeToken identificadorDeToken;
    private TabelaDeSimbolos tabela;
    private ArrayList<String>tokens;


    public ValidadorDeOperacoesAritmeticas(TabelaDeSimbolos tabela) {

        identificadorDeToken = new IdentificadorDeToken();
        this.tabela = tabela;

    }

    public boolean valida(ArrayList<String> tokens){
        this.tokens = tokens;
        boolean validacao = true;
        for(int i = 0;i<tokens.size();i++){
            if(identificadorDeToken.identifica(tokens.get(i)).equals("IDV")){
                 validacao = tabela.simboloExiste(tokens.get(i));
            }
            if(validacao == false)return validacao;
       }
       return validacao;
    }

    @Override
    public String retornaMensagemErro() {
        return null;
    }
}
