package models.Arvore;

import java.util.ArrayList;

public class Arvore {
    Nodo raiz;
    public Arvore() {
        this.raiz = null;
    }
    public Nodo getRaiz() {
        return raiz;
    }
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
    public boolean comparaRaiz(Nodo raiz) {
        boolean retorno = false;
        if(this.getRaiz()==raiz) {
            retorno = true;
        }
        return retorno;
    }
    public boolean comparaArvore(Arvore arvore) {
        boolean retorno = false;
        Nodo raizParaComparar = arvore.getRaiz();
        boolean comparacaoDeRaiz = comparaRaiz(raizParaComparar);
        if(comparacaoDeRaiz){
            Nodo raizDeComparacao = arvore.getRaiz();
            ArrayList<Nodo> filhosParaComparar = raizDeComparacao.getFilhos();
            Nodo raiz = this.getRaiz();
            boolean comparaFilhos = raiz.comparaFilhos(filhosParaComparar);
            retorno = comparaFilhos;
        }
        return retorno;
    }

}
