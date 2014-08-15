package models.Arvore;


public class Variavel {
    String idv;
    boolean isResultado;
    int valorAtual;

    public Variavel(String idv,boolean isResultado,int valorAtual){
        this.setResultado(isResultado);
        this.setValorAtual(valorAtual);
        this.setIdv(idv);
    }

    public boolean isResultado() {
        return isResultado;
    }

    public void setResultado(boolean isResultado) {
        this.isResultado = isResultado;
    }

    public int getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(int valorAtual) {
        this.valorAtual = valorAtual;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }
}
