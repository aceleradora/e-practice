package models.arvore;

import java.util.ArrayList;

public class Nodo {
    private Nodo pai = null;
    private ArrayList<Nodo> filhos = new ArrayList<Nodo>();
    private String token;

    public Nodo(String valor) {
        token = valor;
    }

    public void setPai(Nodo pai) {
        this.pai = pai;
    }

    public ArrayList<Nodo> getFilhos() {
        return filhos;
    }

    public String getToken() {
        return token;
    }

    public Nodo getPai() {
        return pai;
    }

    public Nodo getFilhoComValor(String valor) {
        for(Nodo filho : filhos) {
            if(tokenDoNodoForIgualAoValor(filho, valor)){
                return filho;
            }
        }
        return null;
    }

    private boolean tokenDoNodoForIgualAoValor(Nodo nodo, String valor) {
        return nodo.getToken().equals(valor);
    }

    public void adicionaFilho(Nodo filho) {
        if (filhos.size() < 2) {
            filhos.add(filho);
            filho.setPai(this);
        }
    }

    public boolean comparaFilhos(ArrayList<Nodo> filhosParaComparar) {
        if (listaDeFilhosTemTamanhoDiferenteDaListaParaComparar(filhosParaComparar))
            return false;

        return percorreAsListasComparandoOsNodos(filhosParaComparar);
    }

    private boolean percorreAsListasComparandoOsNodos(ArrayList<Nodo> filhosParaComparar) {
        for (int i = 0; i < filhos.size(); i++) {
            if (nodosSaoDiferentes(filhosParaComparar, i))
                return false;
        }
        return true;
    }

    private boolean nodosSaoDiferentes(ArrayList<Nodo> filhosParaComparar, int indiceDaLista) {
        return filhos.get(indiceDaLista) != filhosParaComparar.get(indiceDaLista);
    }

    private boolean listaDeFilhosTemTamanhoDiferenteDaListaParaComparar(ArrayList<Nodo> filhosParaComparar) {
        return filhos.size() != filhosParaComparar.size();
    }

    public boolean equals(Object o) {
        Nodo nodo = (Nodo) o;
        return (nodo != null && nodo.getToken().equals(token)) ? true : false;
    }
}