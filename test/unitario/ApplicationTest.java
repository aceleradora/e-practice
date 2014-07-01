package unitario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.Application;
import models.SolucaoDoExercicio;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        SolucaoDoExercicio solucaoDoExercicio = new SolucaoDoExercicio("codigo");
        assertNotNull(solucaoDoExercicio.codigo);
    }

    @Test
    public void quandoChamaOMetodoIndexRedirecionaParaSolucoes() {
        Result result = callAction(controllers.routes.ref.Application.index());
        assertThat(status(result)).isPositive();
    }

}
