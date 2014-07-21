package models.analisadorSintatico;

import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import java.util.ArrayList;
import java.util.Arrays;

public class GerenciadorDeValidacao {

    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;
    private ArrayList<String> tokens;
    private ArrayList<String> listaDeMensagensDeErro;

    public GerenciadorDeValidacao(Lexer lexer, IdentificadorDeToken identificadorDeToken, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel, ValidadorDeAtribuicao validadorDeAtribuicao, ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas, ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings) {
        this.lexer = lexer;
        this.identificadorDeToken = identificadorDeToken;
        this.validadorDeDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        this.validadorDeOperacoesAritmeticas = validadorDeOperacoesAritmeticas;
        this.validadorDeConcatenacaoDeStrings = validadorDeConcatenacaoDeStrings;
        this.listaDeMensagensDeErro = new ArrayList<String>();
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
        } else if (listaDeTokensIdentificados.contains("SUBTRACAO")) {
            validadorDeOperacoesAritmeticas.valida(tokens);
        } else if (listaDeTokensIdentificados.contains("MULTIPLICACAO")) {
            validadorDeOperacoesAritmeticas.valida(tokens);
        } else if (listaDeTokensIdentificados.contains("DIVISAO")) {
            validadorDeOperacoesAritmeticas.valida(tokens);
        } else if(listaDeTokensIdentificados.contains("CONCATENACAO")){
            validadorDeConcatenacaoDeStrings.valida(tokens);
        } else if(listaDeTokensIdentificados.get(0).equals("IDV")) {
            validadorDeAtribuicao.valida(tokens);
        }
    }

    public ArrayList<String> mostraMensagensDeErro(){
        listaDeMensagensDeErro.add(validadorDeDeclaracaoDeVariavel.retornaMensagemErro());
        listaDeMensagensDeErro.add(validadorDeAtribuicao.retornaMensagemErro());
        listaDeMensagensDeErro.add(validadorDeOperacoesAritmeticas.retornaMensagemErro());
        listaDeMensagensDeErro.add(validadorDeConcatenacaoDeStrings.retornaMensagemErro());

        listaDeMensagensDeErro.removeAll(Arrays.asList(null, ""));

        return listaDeMensagensDeErro;
    }
}