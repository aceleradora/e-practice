package funcional;

import org.junit.runner.RunWith;
import org.specs2.Specification;
import org.specs2.runner.JUnitRunner;
import play.libs.F;
import org.junit.*;
import play.test.TestBrowser;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ApplicationFunctionalTest {

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