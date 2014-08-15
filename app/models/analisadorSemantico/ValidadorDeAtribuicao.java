        package models.analisadorSemantico;

        import models.TabelaDeSimbolos;
        import models.Validador;
        import models.analisadorLexico.IdentificadorDeToken;
        import java.util.ArrayList;

        public class ValidadorDeAtribuicao implements Validador {
            IdentificadorDeToken identificadorDeToken;
            TabelaDeSimbolos tabelaDeSimbolos;
            ArrayList<String> tokens;

            public ValidadorDeAtribuicao(TabelaDeSimbolos tabelaDeSimbolos) {
                this.tabelaDeSimbolos = tabelaDeSimbolos;
                this.identificadorDeToken = new IdentificadorDeToken();
            }

            public boolean validaVariavel() {
                boolean retorno = false;
                if(tabelaDeSimbolos.simboloExiste(tokens.get(0))) {
                    retorno = true;
                }
                return retorno;
            }

            public boolean ehAtribuicaoDeInteirosValida() {
                return ((identificadorDeToken.verificaSeTodasOsCaracteresSaoNumeros(tokens.get(2))
                        || (identificadorDeToken.identifica(tokens.get(2)).equals("IDV"))
                        && tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(2), "Inteiro"))
                        && tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(0),"Inteiro"));
            }
            public boolean ehAtribuicaoDeStringsSimplesValida() {
                return ((identificadorDeToken.identifica(tokens.get(2)).equals("CONSTANTE_TIPO_STRING"))
                        && tabelaDeSimbolos.verificaSeTipoCombina(tokens.get(0),"String"));
            }

            public boolean validaExpressao() {
                return(ehAtribuicaoDeInteirosValida() || ehAtribuicaoDeStringsSimplesValida());
            }

            @Override
            public boolean valida(ArrayList<String> tokens) {
                this.tokens = tokens;
                return validaVariavel() && validaExpressao();
            }

            @Override
            public String retornaMensagemErro() {
                String erros = "";
                if (!validaVariavel()) {
                    erros = "A variável "+tokens.get(0)+" não foi declarada.";
                    return erros;
                }
                if(!validaExpressao()) {
                    erros = "A variável "+tokens.get(0)+" só aceita atribuição de valores do tipo "+tabelaDeSimbolos.getTipoSimbolo(tokens.get(0))+".";
                    return erros;
                }
                return erros;
            }
        }