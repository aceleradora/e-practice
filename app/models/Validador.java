package models;

import java.util.ArrayList;

public interface Validador {

    public boolean valida(ArrayList<String> tokens);

    public String retornaMensagemErro();

}
