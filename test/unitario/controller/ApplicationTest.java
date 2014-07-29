package unitario.controller;

import org.junit.Ignore;
import org.junit.Test;
import play.libs.F;
import play.mvc.Result;
import play.test.TestBrowser;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static play.test.Helpers.*;

public class ApplicationTest {

    @Test
    public void quandoChamaOMetodoIndexRedirecionaParaOutraRota() {
        Result result;

        result = callAction(controllers.routes.ref.Application.index());

        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }

    @Test
    public void quandoChamaOMetodoSolucaoCarregaUmaViewComRespostaHttp200() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result;

                result = callAction(controllers.routes.ref.Application.solucoes());

                assertThat(status(result)).isEqualTo(OK);
            }
        });
    }
    @Test
    public void rotaSolucoesComMetodoPostDeveRenderizarUmaView() throws Exception {
        running(fakeApplication(), new Runnable() {
            public void run() {
                final HashMap<String, String> fakeForm = new HashMap<String, String>();
                fakeForm.put("solucaoDoUsuario", "var x: Integer");

                Result result = routeAndCall(fakeRequest(POST, "/solucoes").withFormUrlEncodedBody(fakeForm));
                assertThat(result).isNotNull();
            }
        });
    }

    @Test
    public void quandoAcessarRotaInexistenteOResultDeveSerNulo() throws Exception {
        Result result = routeAndCall(fakeRequest(GET, "/rotaInexistente"));
        assertThat(result).isNull();
    }

    @Test
    public void quandoPostaNovaSolucaoRetornaMensagemDeSucesso() throws Exception {

        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                browser.goTo(System.getenv("URL_ENVIRONMENT"));
                browser.fill("#solucaoDoUsuario").with("var x  = 1");
                browser.find("input", withName("valor")).submit();

                assertThat(browser.url()).isEqualTo(System.getenv("URL_ENVIRONMENT")+"/solucoes");
                assertThat(browser.$("#status", 0).getText()).isEqualTo("Status: sua solução foi salva com sucesso!");
            }
        });
    }

    @Test
    public void quandoPostaSolucaoEmBrancoRetornaMensagemDeErro() throws Exception {

        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                browser.goTo(System.getenv("URL_ENVIRONMENT"));
                browser.fill("#solucaoDoUsuario").with("");
                browser.find("input", withName("valor")).submit();

                assertThat(browser.$("#status", 0).getText()).isEqualTo("Status: erro!");
            }
        });
    }

}