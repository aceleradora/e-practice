package models.analisadorLexico;

import models.SolucaoDoExercicio;

import java.util.ArrayList;
import java.util.Arrays;

public class QuebradorDeCodigo {


    public ArrayList<String> quebraLinhas(String solucaoDoUsuario) {

        String[] solucaoDoUsuarioDividida = solucaoDoUsuario.split("\n");
        ArrayList<String> codigoQuebrado = new ArrayList<String>();

        if(!solucaoDoUsuario.contains("\n"))
             codigoQuebrado.add(solucaoDoUsuario);
        else {

            for (int i = 0; i < solucaoDoUsuarioDividida.length; i++) {
                    codigoQuebrado.add(solucaoDoUsuarioDividida[i]);
            }
        }

        codigoQuebrado.removeAll(Arrays.asList(null, ""));
        return codigoQuebrado;
    }
}
