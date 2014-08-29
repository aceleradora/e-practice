package models.analisadorSemantico;
import models.analisadorLexico.Lexer;

import java.util.ArrayList;

public class ValidadorDeVariavelDeResultado{

    private ArrayList<ArrayList<String>> linhas;
    private ArrayList<String> codigo;
    private Lexer lexer;

    public ValidadorDeVariavelDeResultado(ArrayList<String> codigo){
        this.codigo = codigo;
        lexer = new Lexer();
        tokenizaCodigoDaSolucaoDoUsuario();
    }

    public void tokenizaCodigoDaSolucaoDoUsuario() {
        linhas = new ArrayList<ArrayList<String>>();
        for (String linha: codigo) {
            linhas.add(lexer.tokenizar(linha));
        }
    }

    public String retornaIDVDoVarres() {
        String retorno = "varres não foi declarado";
        for (int i = 0; i < linhas.size(); i++) {
            if (linhas.get(i).get(0).equals("varres")){
                retorno =  linhas.get(i).get(1);
            }
        }
        return retorno;
    }

    public boolean valida() {

        if(!retornaIDVDoVarres().equals("varres não foi declarado")){
            for(int i = 0; i < linhas.size();i++){
                if(linhas.get(i).get(0).equals(retornaIDVDoVarres())){
                    return true;
                }
            }
        }
        return false;
    }

    public String retornaMensagemDeErro(){
        String mensagem = "";
        if(!valida()){
            mensagem = "A Solução precisa conter uma variavel de resultado declarada como \"varres\" e com uma atribuição válida.\n";
        }
        return mensagem;
    }
}
