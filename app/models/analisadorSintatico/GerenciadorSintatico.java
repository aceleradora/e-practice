package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class GerenciadorSintatico {

    private Lexer lexer;
    private IdentificadorDeToken identificadorDeToken;
    private ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel;
    private ValidadorDeAtribuicao validadorDeAtribuicao;
    private ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas;
    private ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings;
    private ValidadorGenerico validadorGenerico;
    private Validador validador;
    private ArrayList<String> tokens;

    public GerenciadorSintatico(Lexer lexer, IdentificadorDeToken identificadorDeToken, ValidadorDeDeclaracaoDeVariavel validadorDeDeclaracaoDeVariavel, ValidadorDeAtribuicao validadorDeAtribuicao, ValidadorDeOperacoesAritmeticas validadorDeOperacoesAritmeticas, ValidadorDeConcatenacaoDeStrings validadorDeConcatenacaoDeStrings, ValidadorGenerico validadorGenerico) {
        this.lexer = lexer;
        this.identificadorDeToken = identificadorDeToken;
        this.validadorDeDeclaracaoDeVariavel = validadorDeDeclaracaoDeVariavel;
        this.validadorDeAtribuicao = validadorDeAtribuicao;
        this.validadorDeOperacoesAritmeticas = validadorDeOperacoesAritmeticas;
        this.validadorDeConcatenacaoDeStrings = validadorDeConcatenacaoDeStrings;
        this.validadorGenerico = validadorGenerico;
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

        //listaDeTokensIdentificados.get(0).equals("PALAVRA_RESERVADA") || listaDeTokensIdentificados.get(2).equals(":")
        if (listaDeTokensIdentificados.get(0).equals("PALAVRA_RESERVADA") || listaDeTokensIdentificados.contains("TIPO_DE_VARIAVEL")) {
            validadorDaExpressao = this.validadorDeDeclaracaoDeVariavel;
        } else if (listaDeTokensIdentificados.contains("ADICAO")) {
            if((listaDeTokensIdentificados.size() <= 4) && (listaDeTokensIdentificados.get(2) == "ADICAO")) {
                validadorDaExpressao = this.validadorDeAtribuicao;
            }
            else {
                validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
            }
        } else if (listaDeTokensIdentificados.contains("SUBTRACAO")) {
            if((listaDeTokensIdentificados.size() <= 4) && (listaDeTokensIdentificados.get(2) == "SUBTRACAO")) {
                validadorDaExpressao = this.validadorDeAtribuicao;
            }
            else {
                validadorDaExpressao = this.validadorDeOperacoesAritmeticas;
            }
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
        } else if(listaDeTokensIdentificados.contains("IGUAL")){
            validadorDaExpressao = validadorDeAtribuicao;
        } else {
            validadorDaExpressao = validadorGenerico;
        }

        return validadorDaExpressao;
    }

}