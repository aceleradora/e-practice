package models.Arvore;

import java.util.ArrayList;

public class Fila<T> {

    private  ArrayList<T> fila;

    public Fila() {
        fila = new ArrayList<T>();
    }

    public void adicionaItem(T item) {
        fila.add(item);
    }

    public boolean removeItem() {
        if(!fila.isEmpty()) {
            fila.remove(0);
            return true;
        }
        return false;
    }

    public T primeiroDaFila() {
        if(!fila.isEmpty())
            return fila.get(0);
        return null;
    }

    public int tamanhoDaFila(){
        return fila.size();
    }

}