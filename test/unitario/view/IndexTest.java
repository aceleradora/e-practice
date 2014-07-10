package unitario.view;

import models.SolucaoDoExercicio;
import org.junit.Test;
import play.data.Form;
import play.mvc.Content;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;

public class IndexTest {
    private List<SolucaoDoExercicio> solucoes = new ArrayList<SolucaoDoExercicio>();
    private Form<SolucaoDoExercicio> formPreenchido = Form.form(SolucaoDoExercicio.class);

    @Test
    public void quandoChamaOMetodoSolucaoVindoDeUmErroCarregaAViewIndexComMensagemDeErro() {
        Content html = views.html.index.render(solucoes, formPreenchido, "Status: erro!", "");

        assertThat(contentAsString(html)).contains("Status: erro!");
    }
}
