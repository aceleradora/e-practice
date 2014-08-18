package models.analisadorLexico;
import java.util.ArrayList;
import java.util.Arrays;

public class QuebradorDeCodigoEmLinhas {

    private ArrayList<String> codigoQuebrado;

    public ArrayList<String> quebra(String solucaoDoUsuario) {

        String[] solucaoDoUsuarioDividida = solucaoDoUsuario.split("[\n?\r]+");
        codigoQuebrado = new ArrayList<String>();

        if(!solucaoDoUsuario.contains("\n") && !solucaoDoUsuario.contains("\r"))
             codigoQuebrado.add(solucaoDoUsuario);
        else {

            for (int i = 0; i < solucaoDoUsuarioDividida.length; i++) {
                    codigoQuebrado.add(solucaoDoUsuarioDividida[i]);
            }
        }

        removeEspacosEmBrancoDoArray();

        return codigoQuebrado;
    }

    private void removeEspacosEmBrancoDoArray() {
        codigoQuebrado.removeAll(Arrays.asList(null, ""));
    }
}
