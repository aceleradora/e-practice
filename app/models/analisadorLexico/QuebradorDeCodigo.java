package models.analisadorLexico;

import models.SolucaoDoExercicio;

import java.util.ArrayList;

public class QuebradorDeCodigo {

    public String quebra(String solucaoDoUsuario) {

        if(!solucaoDoUsuario.contains("\n"))
            return solucaoDoUsuario;
        else
            return null;
    }


    public ArrayList<String> quebraDuasLinhas(String solucaoDoUsuario) {

        String[] solucaoDoUsuarioDividida = solucaoDoUsuario.split("\n");

        ArrayList<String> codigoQuebrado = new ArrayList<String>();

        for(int i = 0; i < solucaoDoUsuarioDividida.length; i++){
            codigoQuebrado.add(solucaoDoUsuarioDividida[i]);
        }

        return codigoQuebrado;
    }
}
