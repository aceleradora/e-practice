package models.Arvore;

import models.TabelaDeSimbolos;
import models.analisadorLexico.IdentificadorDeToken;
import models.analisadorLexico.Lexer;
import models.analisadorLexico.QuebradorDeCodigoEmLinhas;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;


public class ConstrutorDeArvore {
    QuebradorDeCodigoEmLinhas quebradorDeCodigoEmLinhas;
    ArrayList<String> linhas;
    ArrayList<Arvore> arvores;
    Lexer lexer;
    TabelaDeValores tabelaDeValores;
    TabelaDeSimbolos tabelaDeSimbolos;
    IdentificadorDeToken identificadorDeToken;


    public ConstrutorDeArvore(QuebradorDeCodigoEmLinhas quebradorDeCodigoEmLinhas, String solucaoDoExercicio, TabelaDeSimbolos tabelaDeSimbolos){
        this.quebradorDeCodigoEmLinhas = quebradorDeCodigoEmLinhas;
        linhas = quebradorDeCodigoEmLinhas.quebra(solucaoDoExercicio);
        this.tabelaDeSimbolos = tabelaDeSimbolos;


    }

    public QuebradorDeCodigoEmLinhas getQuebradorDeCodigoEmLinhas() {
        return quebradorDeCodigoEmLinhas;
    }

    public ArrayList<String> getLinhas() {
        return linhas;
    }

    public Arvore constroi(ArrayList<String> linhaTokenizada) {
        //selecionaAsLinhasQueImportamParaAsArvores
        if(linhaTokenizada.get(0) == "var" && tabelaDeSimbolos.simboloExiste(linhaTokenizada.get(1)) && tabelaDeSimbolos.verificaSeTipoCombina(linhaTokenizada.get(1),"Inteiro")){
            Variavel variavel = new Variavel(linhaTokenizada.get(1), false, 0);
            tabelaDeValores.add(variavel);
        } else if(linhaTokenizada.get(0) == "varres" && tabelaDeSimbolos.simboloExiste(linhaTokenizada.get(1))&& tabelaDeSimbolos.verificaSeTipoCombina(linhaTokenizada.get(1),"Inteiro")){
            Variavel variavel = new Variavel(linhaTokenizada.get(1), true, 0);
            tabelaDeValores.add(variavel);
        } else if(tabelaDeSimbolos.simboloExiste(linhaTokenizada.get(0)) && tabelaDeSimbolos.verificaSeTipoCombina(linhaTokenizada.get(0), "Inteiro")){
            if(identificadorDeToken.identifica(linhaTokenizada.get(1)) == "IGUAL"){
                   estruturaArvore(linhaTokenizada);
            }
        }

        return null;
    }

    public ArrayList<String> estruturaArvore(ArrayList<String> linhaTokenizada) {
        Stack stack = new Stack();
        Fila<String> fila = new Fila<String>();

        Arvore arvore = new Arvore();
        ArrayList<String> subLinhaTokenizada = new ArrayList<String>();
        for(int i = 2; i < linhaTokenizada.size(); i++){
            subLinhaTokenizada.add(linhaTokenizada.get(i));
        }

        for (int i = 0;i<subLinhaTokenizada.size();i++){
            if(identificadorDeToken.identifica(subLinhaTokenizada.get(i)) == "NUMERO"||
               identificadorDeToken.identifica(subLinhaTokenizada.get(i)) == "IDV") {
                   fila.adicionaItem(subLinhaTokenizada.get(i));

            }else if(stack.empty()){
                if(identificadorDeToken.identifica(subLinhaTokenizada.get(i)) == "ADICAO"||
                   identificadorDeToken.identifica(subLinhaTokenizada.get(i)) == "SUBTRACAO"||
                   identificadorDeToken.identifica(subLinhaTokenizada.get(i)) == "MULTIPLICACAO"||
                   identificadorDeToken.identifica(subLinhaTokenizada.get(i)) == "DIVISAO"){
                   stack.push(subLinhaTokenizada.get(i));
                }
            }
        }


        return subLinhaTokenizada;

    }



}
