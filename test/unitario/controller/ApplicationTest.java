package unitario.controller;

import com.google.common.collect.ImmutableMap;
import controllers.Application;
import controllers.routes;
import models.SolucaoDoExercicio;
import org.junit.Before;
import org.junit.Test;
import play.api.data.Form;
import play.libs.F;
import play.mvc.*;
import play.test.FakeApplication;
import play.test.FakeRequest;
import play.test.TestBrowser;
import scalaz.std.java.util.concurrent.callable;
import views.html.helper.form;

import javax.xml.ws.spi.http.HttpContext;

import static org.fluentlenium.core.filter.FilterConstructor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

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
        HashMap<String, String> fakeForm = new HashMap<String, String>();
        fakeForm.put("solucaoDoUsuario", "var x: Integer");

        Result result = routeAndCall(fakeRequest(POST, "/solucoes").withFormUrlEncodedBody(fakeForm));
        assertThat(result).isNotNull();
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