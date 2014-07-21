package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class GerenciadorDeValidacao {

    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;
    private ArrayList<String> tokens;

    public GerenciadorDeValidacao(Lexer lexer, IdentificadorDeToken identificadorDeToken, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel, ValidadorDeAtribuicao validadorDeAtribuicao, ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas, ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings) {
        this.lexer = lexer;
        this.identificadorDeToken = identificadorDeToken;
        this.validadorDeDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        this.validadorDeOperacoesAritmeticas = validadorDeOperacoesAritmeticas;
        this.validadorDeConcatenacaoDeStrings = validadorDeConcatenacaoDeStrings;
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
        } else if (listaDeTokensIdentificados.contains("ADICAO")) {
            validadorDeOperacoesAritmeticas.valida(tokens);
        } else if(listaDeTokensIdentificados.contains("CONSTANTE_TIPO_STRING")){
            validadorDeConcatenacaoDeStrings.valida(tokens);
        } else if(listaDeTokensIdentificados.get(0).equals("IDV")) {
            validadorDeAtribuicao.valida(tokens);
        }
    }

}