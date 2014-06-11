import play.libs.F;
import org.junit.*;
import play.test.TestBrowser;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

public class ApplicationFunctionalTest {

    @Test
    public void verificaSeOTituloEIgualAePractice() {
        running(testServer(3333), HTMLUNIT, new F.Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://e-practice.herokuapp.com/");
                assertThat(browser.$("title").getTexts().get(0)).isEqualTo("e-Practice");
                //browser.$("a").click();
                //assertThat(browser.url()).isEqualTo("http://localhost:3333/Coco");
                //assertThat(browser.$("#title", 0).getText()).isEqualTo("Hello Coco");
            }
        });
    }

}