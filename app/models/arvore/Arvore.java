package models.arvore;

public class Arvore {
    private Nodo raiz;

    public Arvore() {
        raiz = null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public boolean comparaRaiz(Nodo raiz) {
        return this.raiz == raiz ? true : false;
    }

    public boolean comparaArvore(Arvore arvoreParaComparar) {
        return raizEFilhosSaoIguais(arvoreParaComparar) ? true : false;
    }

    private boolean raizEFilhosSaoIguais(Arvore arvoreParaComparar) {
        return comparaRaiz(arvoreParaComparar.getRaiz()) && raiz.comparaFilhos(arvoreParaComparar.getRaiz().getFilhos());
    }
}