package unitario;

import models.SolucaoDoExercicio;
import models.parser.IdentificadorDeToken;
import models.parser.Lexer;
import models.parser.TabelaDeSimbolos;
import org.junit.*;

import play.mvc.*;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

    @Test
    public void testandoSnapCI() {
        assertThat(true);
    }

    @Test
    public void SolucaoNaoPodeTerCorpoVazio() {
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio("solucaoDoUsuario");
        assertNotNull(solucaoDoExercicio.solucaoDoUsuario);
    }

    @Test
    public void quandoChamaOMetodoIndexRetornaPositivo() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isPositive();
    }

    @Test
    public void quandoAtribuiInteiroAVarDoTipoInteiroRetornaTrue() throws Exception {
        TabelaDeSimbolos tabelaDeSimbolos = new TabelaDeSimbolos();
        tabelaDeSimbolos.adicionaSimbolo("x", "Inteiro");

        boolean resultado = tabelaDeSimbolos.verifica("x", "Inteiro");

        assertThat(resultado, is(true));
    }

}
