package models.Arvore;

import java.util.ArrayList;


public class TabelaDeValores {
    ArrayList<Variavel> tabelaDeValores;


    public void add(Variavel variavel){
        tabelaDeValores.add(variavel);
    }

    public Variavel get(String idv){
        Variavel var = null;
        for(Variavel variavel:tabelaDeValores){
            if(variavel.getIdv() == idv){
                var = variavel;
            }
        }
        return var;
    }
}
