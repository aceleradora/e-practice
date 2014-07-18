package models;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorSintatico.ValidadorDeAtribuicao;
import models.analisadorSintatico.ValidadorDeDeclaracaoDeVariavel;
import java.util.ArrayList;

public class GerenciadorDeValidacao {

    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ArrayList<String> tokens;

    public GerenciadorDeValidacao(Lexer lexer, IdentificadorDeToken identificadorDeToken, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel, ValidadorDeAtribuicao validadorDeAtribuicao) {
        this.lexer = lexer;
        this.identificadorDeToken = identificadorDeToken;
        this.validadorDeDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
    }

    public void interpreta(String sentenca) {
        tokens = lexer.tokenizar(sentenca);
        ArrayList<String> listaDeTokensIdentificados = new ArrayList<String>();

        for (int i = 0; i < tokens.size(); i++) {
            String tokenIdentificado = identificadorDeToken.identifica(tokens.get(i));
            listaDeTokensIdentificados.add(tokenIdentificado);
        }

        if (listaDeTokensIdentificados.get(0).equals("PALAVRA_RESERVADA")) {
            validadorDeDeclaracaoDeVariavel.valida(tokens);
        }

        if(listaDeTokensIdentificados.get(0).equals("IDV")) {
            validadorDeAtribuicao.valida(tokens);
        }
    }

    public boolean verificaSeEhDeclaracao(ArrayList<String> tokens) {
        if (validadorDeDeclaracaoDeVariavel.valida(tokens))
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