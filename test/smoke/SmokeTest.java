package smoke;

import org.junit.Test;
import play.libs.F;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class SmokeTest {

    @Test
    public void verificaSeOTituloEIgualAePractice() {
        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo(System.getenv("URL_ENVIRONMENT"));
                assertThat(browser.$("title").getTexts().get(0)).isEqualTo("e-Practice");
            }
        });
    }

}