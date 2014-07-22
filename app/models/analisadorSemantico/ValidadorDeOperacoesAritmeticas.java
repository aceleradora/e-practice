package models.analisadorSemantico;

import models.Validador;

import java.util.ArrayList;

/**
 * Created by aluno6 on 22/07/14.
 */
public class ValidadorDeOperacoesAritmeticas implements Validador{


    public boolean valida(ArrayList<String> tokens) {
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        return null;
    }
}
