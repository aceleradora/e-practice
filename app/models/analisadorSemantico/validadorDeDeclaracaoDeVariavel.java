package models.analisadorSemantico;

import models.Validador;

import java.util.ArrayList;

/**
 * Created by alunos4 on 21/07/14.
 */
public class validadorDeDeclaracaoDeVariavel implements Validador {

    @Override
    public boolean valida(ArrayList<String> tokens) {
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        return null;
    }
}
