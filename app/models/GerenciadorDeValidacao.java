package models;

import javassist.compiler.Lex;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import models.analisadorSintatico.ValidadorDeOperacoesAritmeticas;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class GerenciadorDeValidacao {

    TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
    Lexer lexer = new Lexer();
    IdentificadorDeToken identificadorDeToken = new IdentificadorDeToken();

    ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracao = new ValidadorDeDeclaracaoDeVariavel(tabelaDeSimbolos);
    ValidadorDeAtribuicao validadorDeAtribuicao = new ValidadorDeAtribuicao(lexer, identificadorDeToken);

    public boolean verificaSeEhDeclaracao(ArrayList<String> tokens) {
        if (validadorDeDeclaracao.valida(tokens))
            return true;
        else
            return false;
    }


    public boolean verificaSeEhAtribuicao(String codigo) {

        return true;

    }

    public boolean verificaSeEhOperacaoAritmetica(ArrayList<String> tokens) {

        return true;
    }

    public boolean verificaSeEhAtribuicaoDeString(String codigo) {

        return true;
    }

}