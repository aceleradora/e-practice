package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import java.util.ArrayList;
import java.util.Arrays;

public class GerenciadorSintatico {

    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;
    private Validador validador;
    private ArrayList<String> tokens;
    private ArrayList<String> listaDeMensagensDeErro;

    public GerenciadorSintatico(Lexer lexer, IdentificadorDeToken identificadorDeToken, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel, ValidadorDeAtribuicao validadorDeAtribuicao, ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas, ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings) {
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

        validador = tipoValidador(listaDeTokensIdentificados);
        validador.valida(tokens);
    }

    public String mostraMensagensDeErro(){
        return validador.retornaMensagemErro();
    }

    private Validador tipoValidador(ArrayList<String> listaDeTokensIdentificados){
        Validador validadorDaExpressao;

        if (listaDeTokensIdentificados.get(0).equals("PALAVRA_RESERVADA") || listaDeTokensIdentificados.get(2).equals(":")) {
            validadorDaExpressao = this.validadorDeDeclaracaoDeVariavel;
        } else if (listaDeTokensIdentificados.contains("ADICAO")) {
            validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
        } else if (listaDeTokensIdentificados.contains("SUBTRACAO")) {
            validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
        } else if (listaDeTokensIdentificados.contains("MULTIPLICACAO")) {
            validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
        } else if (listaDeTokensIdentificados.contains("DIVISAO")) {
            validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
        } else if(listaDeTokensIdentificados.contains("NUMERO")) {
            validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
        } else if(listaDeTokensIdentificados.contains("CONCATENACAO")){
            validadorDaExpressao = this.validadorDeConcatenacaoDeStrings;
        } else if(listaDeTokensIdentificados.get(0).equals("IDV")) {
            validadorDaExpressao = this.validadorDeAtribuicao;
        } else {
            validadorDaExpressao = null;
        }

        return validadorDaExpressao;
    }

}