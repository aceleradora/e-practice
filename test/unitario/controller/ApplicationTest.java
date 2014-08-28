package unitario.controller;

import org.junit.Test;
import play.libs.F;
import play.mvc.Result;
import play.test.TestBrowser;

import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class ApplicationTest {
    @Test
    public void quandoChamaOMetodoIndexRedirecionaParaOutraRota() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result;

                result = callAction(controllers.routes.ref.Application.index());

                assertThat(status(result)).isEqualTo(SEE_OTHER);
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
                browser.fill("#solucaoDoUsuario").with("varres x : Inteiro\nx = 42");
                browser.$("#botaoDeEnviar").click();
                browser.$("#solucaoDoUsuario").submit();

                assertThat(browser.url()).isEqualTo(System.getenv("URL_ENVIRONMENT")+"/solucoes");
                assertThat(browser.$("#status", 0).getText()).isEqualTo("Status: sua solução foi salva com sucesso!");
            }
        });
    }

    @Test
    public void mantemASolucaoNaCaixaDeTextoAposEnviarASolucao() throws Exception {
        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) throws Throwable {
                browser.goTo(System.getenv("URL_ENVIRONMENT"));
                browser.fill("#solucaoDoUsuario").with("var esseTextoSeraMantido : String");

                browser.find("button", withName("valor")).click();

                String textoDaTextArea = browser.$("#solucaoDoUsuario").getText();
                assertThat(textoDaTextArea).contains("var esseTextoSeraMantido : String");
            }
        });
    }

    @Test
    public void quandoPostaSolucaoEmBrancoRetornaMensagemDeErro() throws Exception {
        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {

                browser.goTo(System.getenv("URL_ENVIRONMENT"));
                browser.fill("#solucaoDoUsuario").with("");
                browser.$("#botaoDeEnviar").click();
                browser.$("#solucaoDoUsuario").submit();
                String label = browser.$("#status").getText();

                assertThat(label).isEqualTo("Status: erro!");

            }
        });
    }

}