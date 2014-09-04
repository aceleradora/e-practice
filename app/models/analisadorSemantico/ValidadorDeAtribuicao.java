package models.analisadorSemantico;

import models.TabelaDeSimbolos;
import models.Validador;
import models.analisadorLexico.IdentificadorDeToken;
import java.util.ArrayList;

public class ValidadorDeAtribuicao implements Validador {
    IdentificadorDeToken identificadorDeToken;
    TabelaDeSimbolos tabelaDeSimbolos;
    ArrayList<String> tokens;

    public ValidadorDeAtribuicao(TabelaDeSimbolos tabelaDeSimbolos) {
        this.tabelaDeSimbolos = tabelaDeSimbolos;
        this.identificadorDeToken = new IdentificadorDeToken();
    }

    public boolean valida(ArrayList<String> tokens) {
        this.tokens = tokens;
        if (validaVariavel() && validaExpressao()) {
            if (tokens.size() == 3) {
                if (identificadorDeToken.identifica(tokens.get(2)).equals("NUMERO") || identificadorDeToken.identifica(tokens.get(2)).equals("CONSTANTE_TIPO_STRING"))
                    tabelaDeSimbolos.atualizaValor(tokens.get(0), tokens.get(2));
                else
                    tabelaDeSimbolos.atualizaValor(tokens.get(0), tabelaDeSimbolos.getValor(tokens.get(2)));
                return true;
            } else {
                if (identificadorDeToken.identifica(tokens.get(3)).equals("NUMERO")) {
                    String valorComUnario = tokens.get(2) + tokens.get(3);
                    tabelaDeSimbolos.atualizaValor(tokens.get(0), valorComUnario);
                } else {
                    String valorAtualizado = atualizaValorDaVariavel();
                    tabelaDeSimbolos.atualizaValor(tokens.get(0), valorAtualizado);
                }
                return true;
            }
       }
        return false;
    }

    private String atualizaValorDaVariavel() {
        if (tokens.get(2).equals("-")) {
            String valorContidoNaVariavel = tabelaDeSimbolos.getValor(tokens.get(3));
            int valorEmInteiro = Integer.parseInt(valorContidoNaVariavel);
            int valorComSinalNegativo = valorEmInteiro * -1;
            return String.valueOf(valorComSinalNegativo);
        } else {
            return tabelaDeSimbolos.getValor(tokens.get(3));
        }
    }

    public String retornaMensagemErro() {
        if (!validaVariavel()) {
            return "A variável "+tokens.get(0)+" não foi declarada.";
        }
        if(!validaExpressao()) {
            return "A variável "+tokens.get(0)+" só aceita atribuição de valores do tipo "+tabelaDeSimbolos.getTipoSimbolo(tokens.get(0))+".";
        }
        return "";
    }

    private boolean validaVariavel() {
        return tabelaDeSimbolos.simboloExiste(tokens.get(0));
    }

    private boolean validaExpressao() {
        return(ehAtribuicaoDeInteirosValida() || ehAtribuicaoDeStringsSimplesValida());
    }

    private boolean ehAtribuicaoDeInteirosValida() {
        return (verificaSeSegundoTokenEhNumeroOuVariavelOuUnarioValida()) && primeiroTokenEhVariavelDoTipoInteiro();
    }

    private boolean primeiroTokenEhVariavelDoTipoInteiro() {
        return tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(0), "Inteiro");
    }

    private boolean verificaSeSegundoTokenEhNumeroOuVariavelOuUnarioValida() {
        if (tokens.size() == 4) {
            return (identificadorDeToken.identifica(tokens.get(2)).equals("ADICAO") || identificadorDeToken.identifica(tokens.get(2)).equals("SUBTRACAO")) && (identificadorDeToken.verificaSeTodasOsCaracteresSaoNumeros(tokens.get(3))) || tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(3), "Inteiro");
        } else {
            return identificadorDeToken.verificaSeTodasOsCaracteresSaoNumeros(tokens.get(2))
                    || tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(2), "Inteiro");
        }
    }

    private boolean ehAtribuicaoDeStringsSimplesValida() {
        return (verificaSeSegundoTokenEhConstanteStringOuVariavelString()) && primeiroTokenEhVariavelDoTipoString();
    }

    private boolean primeiroTokenEhVariavelDoTipoString() {
        return tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(0), "String");
    }

    private boolean verificaSeSegundoTokenEhConstanteStringOuVariavelString() {
        return identificadorDeToken.identifica(tokens.get(2)).equals("CONSTANTE_TIPO_STRING")
                || tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(2), "String");
    }
}