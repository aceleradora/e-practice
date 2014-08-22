package models.Arvore;

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

    public Nodo getFilho(String valor) {
        for(Nodo filho: filhos){
            if(filho.getToken() == valor){
                return filho;
            }
        }
        return null;
    }

    public void adicionaFilho(Nodo filho) {
       filhos.add(filho);
        filho.setPai(this);
    }

    public boolean comparaFilhos(ArrayList<Nodo> filhosParaComparar) {
        boolean retorno = false;
        for(Nodo filho: filhos){
            for(Nodo outroFilho: filhos){
                if( filho == outroFilho){
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public Nodo getPai() {
        return pai;
    }
}