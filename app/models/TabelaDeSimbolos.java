package models;

import java.util.ArrayList;
import java.util.HashMap;

public class TabelaDeSimbolos {
    private HashMap<String, String> tabela;
    private ArrayList<String> ponteirosParaVariaveisDeResultado;

    public TabelaDeSimbolos() {
        tabela = new HashMap<String, String>();
        ponteirosParaVariaveisDeResultado = new ArrayList<String>();
    }

    public boolean adicionaSimbolo(String identificador, String tipo){
        tabela.put(identificador, tipo);
        return true;
    }

    public boolean verificaSeTipoCombina(String idv, String tipo) {
        if(tipo.equals(tabela.get(idv))) return true;
        else return false;
    }

    public boolean simboloExiste(String simbolo) {
        String simboloDaTabela;

        simboloDaTabela = tabela.get(simbolo);
        if (simboloDaTabela != null){
            return true;
        }
        else {
            return false;
        }
    }

    public String getTipoSimbolo(String simbolo) {
        String tipoDoSimboloNaTabela = tabela.get(simbolo);
        return tipoDoSimboloNaTabela;
    }

    public void adicionaVariavelDeResultado(String identificador) {
        ponteirosParaVariaveisDeResultado.add(identificador);
    }

    public boolean verificaSeExisteVariavelDeResultado(String identificador) {
        return ponteirosParaVariaveisDeResultado.contains(identificador);
    }
}