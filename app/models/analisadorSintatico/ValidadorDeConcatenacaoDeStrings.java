package models.analisadorSintatico;

import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeConcatenacaoDeStrings implements Validador {

    ArrayList<String> tokens;
    IdentificadorDeToken identificador;
    private boolean variavel;
    private boolean constanteString;

    public ValidadorDeConcatenacaoDeStrings() {
        identificador = new IdentificadorDeToken();
    }

    private String validaTerceiroToken() {
        if (comparaToken(2,"IDV") || comparaToken(2,"CONSTANTE_TIPO_STRING")) {
            return "O terceiro token é válido.";
        } else {
            return "Você digitou \""+ tokens.get(2) +"\" e deveria ser uma variável ou uma constante do tipo String.";
        }
    }

    private boolean comparaToken(int indice, String valor) {
        return getTokenIdentificado(indice).equals(valor);
    }

    private String getTokenIdentificado(int indice) {
        return identificador.identifica(tokens.get(indice));
    }

    private int quantidadeDeTokensDepoisDoSinalDeIgual() {
        return (tokens.size() - 2);
    }

    private String validaTokensDepoisDoIgual() {

        if(quantidadeDeTokensDepoisDoSinalDeIgual() > 1) {
            for (int i = 2; i < tokens.size(); i++){
                variavel = comparaToken(i,"IDV");
                constanteString = comparaToken(i,"CONSTANTE_TIPO_STRING");
                boolean simboloConcatenacao = comparaToken(i,"CONCATENACAO");

                if((indiceEPar(i)) && naoEVariavelNemConstante()) {
                    return "Você digitou \""+ tokens.get(i) + "\" e deveria ser uma variável ou constante String.";
                }
                if((!indiceEPar(i)) && (!simboloConcatenacao)) {
                    return "Você digitou \"" + tokens.get(i) + "\" e deveria ser \"<>\".";
                }
            }
        } else {
            return validaTerceiroToken();
        }
        return "A concatenação foi feita corretamente.";
    }

    private boolean naoEVariavelNemConstante() {
        return !((variavel) || (constanteString));
    }

    private boolean indiceEPar(int i) {
        return (i % 2) == 0;
    }

    @Override
    public boolean valida(ArrayList<String> tokens) {
        this.tokens = tokens;

        if(retornaMensagemErro() == null || retornaMensagemErro().equals("")){
            return true;
        }
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        ArrayList<String> mensagens = new ArrayList<String>();
        String mensagensDeRetorno = "";

        if((!validaTerceiroToken().equals("O terceiro token é válido.")) && quantidadeDeTokensDepoisDoSinalDeIgual() == 1) {
            mensagens.add(validaTerceiroToken());
        }
        if(!validaTokensDepoisDoIgual().equals("A concatenação foi feita corretamente.")) {
            mensagens.add(validaTokensDepoisDoIgual());
        }

        for(int i = 0; i < mensagens.size(); i++) {
            if(!(mensagens.isEmpty() && mensagens.equals("")))
                mensagensDeRetorno = (i + 1) + ") " + mensagens.get(i) + "\n";
        }

        return mensagensDeRetorno;
    }
}