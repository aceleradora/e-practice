package unitario.model.analisadorSemantico;

import models.Validador;

import java.util.ArrayList;

public class ValidadorGenerico implements Validador {

    @Override
    public boolean valida(ArrayList<String> tokens) {
        return false;
    }

    @Override
    public String retornaMensagemErro() {
        return "Código inválido. Sintaxe não reconhecida.";
    }
}
