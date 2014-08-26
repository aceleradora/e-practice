package models;

import java.util.ArrayList;
import java.util.HashMap;

public class TabelaDeSimbolos {
    private static final String VALOR_PADRAO_STRING = "";
    private static final String VALOR_PADRAO_INTEIRO = "0";
    private HashMap<String, String> tabelaDeTipos;
    private HashMap<String, String> tabelaDeValores;
    private ArrayList<String> ponteirosParaVariaveisDeResultado;

    public TabelaDeSimbolos() {
        tabelaDeTipos = new HashMap<String, String>();
        tabelaDeValores = new HashMap<String, String>();
        ponteirosParaVariaveisDeResultado = new ArrayList<String>();
    }

    public boolean adicionaSimbolo(String identificador, String tipo){
        tabelaDeTipos.put(identificador, tipo);
        if (tipo.equals("Inteiro"))
            tabelaDeValores.put(identificador, VALOR_PADRAO_INTEIRO);
        else
            tabelaDeValores.put(identificador, VALOR_PADRAO_STRING);
        return true;
    }

    public boolean verificaSeTipoCombina(String idv, String tipo) {
        return tipo.equals(tabelaDeTipos.get(idv));
    }

    public boolean simboloExiste(String simbolo) {
        return tabelaDeTipos.get(simbolo) != null;
    }

    public String getTipoSimbolo(String simbolo) {
        String tipoDoSimboloNaTabela = tabelaDeTipos.get(simbolo);
        return tipoDoSimboloNaTabela;
    }

    public void adicionaVariavelDeResultado(String identificador) {
        ponteirosParaVariaveisDeResultado.add(identificador);
    }

    public String getValor(String identificador) {
        return tabelaDeValores.get(identificador);
    }

    public void atualizaValor(String identificador, String novoValor) {
        tabelaDeValores.put(identificador, novoValor);
    }
}