package models.analisadorSintatico;

import models.TabelaDeSimbolos;
import java.util.ArrayList;

/**
 * Created by alunos3 on 15/07/14.
 */
public interface interfaceValidador {

    public String validaPrimeiroToken();

    public String validaSegundoToken();

    public String validaTerceiroToken();

    public int quantosTokensTemDepoisDoIgual();

    public String validaTokensDepoisDoIgual();

    public ArrayList<String> getMensagens();

    public boolean valida();

    public void setTabelaDeSimbolos(TabelaDeSimbolos tabelaDeSimbolos);

    public TabelaDeSimbolos getTabelaDeSimbolos();

    public boolean validaPrimeiroToken(String primeiroToken);

    public String mensagemDeErroNoPrimeiroToken(String frase);

    public boolean validaSegundoToken(String segundoToken);

    public String mensagemDeErroNoSegundoToken(String frase);

    public boolean validaTerceiroToken(String terceiroToken);

    public String mensagemDeErroNoTerceiroToken(String frase);

    public String mensagemDeErro(String frase);

    public boolean validaIdv(String frase);

    public boolean valida(String frase);

    public boolean validaSePrimeiroTokenEVar();

    public boolean validaSeSegundoTokenEIdv();

    public boolean validaSeTerceiroTokenEDoisPontos();

    public boolean validaSeQuartoTokenETipoDeVariavel();

    public boolean verificaSeTokensTemQuantidadeAcimaDoEsperado();

    public boolean verificaSeTokensTemQuantidadeAbaixoDoEsperado();

    public void adicionaVariavelNaTabelaDeSimbolos();

    public String capturaMensagensDeErro();


    public String validarOperacoesAritmeticasSemInsercaoDeParenteses();

    public String verificaSeOperacaoContemUmParenteseAberto();

    public String verificaSeOperacaoContemUmParenteseFechado();

    public String verificaSeOperacaoContemUmParenteseAbertoEFechado();

    public String verificaSePrimeiroParenteseEncontradoEhUmParenteseAberto();

    public String verificaSeHaConteudoDentroDoParenteses();

    public String verificaSeOperacaoContemAMesmaQuandidadeDeParentesesAbertoEFechado();
}
