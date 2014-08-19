package models.Arvore;

import java.util.ArrayList;

//public class Nodo{
//    private Nodo pai;
//    private Nodo filhoEsquerda;
//    private Nodo filhoDireita;
//    private String token;
//    public Nodo(String token) {
//        pai = null;
//        filhoEsquerda = null;
//        filhoDireita = null;
//        this.token = token;
//    }
//    public Nodo getFilhoDireita() {
//        return filhoDireita;
//    }
//    public void setFilhoDireita(Nodo nodo) {
//        filhoDireita = nodo;
//    }
//    public Nodo getFilhoEsquerda() {
//        return filhoEsquerda;
//    }
//    public void setFilhoEsquerda(Nodo nodo) {
//        filhoEsquerda = nodo;
//    }
//    public Nodo getPai() {
//        return pai;
//    }
//    public void setPai(Nodo nodo) {
//        pai = nodo;
//    }
//    public String getToken() {
//        return token;
//    }
//    public void setToken(String token) {
//        this.token = token;
//    }
//}

public class Nodo {
    Nodo pai = null;
//    Nodo filhoEsquerda;
//    Nodo filhoDireita;
    ArrayList<Nodo> filhos = new ArrayList<Nodo>();
    String token;
    public Nodo(String valor) {
        this.token = valor;
    }
    public Nodo getPai() {
        return pai;
    }
    public void setPai(Nodo pai) {
        this.pai = pai;
    }
    public ArrayList<Nodo> getFilhos() {
        return filhos;
    }
    public void setFilhos(ArrayList<Nodo> filhos) {
        this.filhos = filhos;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
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
}