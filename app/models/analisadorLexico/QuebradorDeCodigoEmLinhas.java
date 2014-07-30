package models.analisadorLexico;
import java.util.ArrayList;
import java.util.Arrays;

public class QuebradorDeCodigoEmLinhas {

    public ArrayList<String> quebra(String solucaoDoUsuario) {

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
