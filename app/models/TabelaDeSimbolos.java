package models;

import java.util.HashMap;

public class TabelaDeSimbolos {
    private HashMap<String, String> tabela;

    public TabelaDeSimbolos() {
        tabela = new HashMap<String, String>();
    }

    public void adicionaSimbolo(String identificador, String tipo){
        tabela.put(identificador, tipo);
    }


    public boolean verificaSeTipoCombina(String idv, String tipo) {
        if(tipo.equals(tabela.get(idv))) return true;
        else return false;
    }
}
