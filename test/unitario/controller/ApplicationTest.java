package unitario.controller;

import controllers.Application;
import controllers.routes;
import models.SolucaoDoExercicio;
import org.junit.Before;
import org.junit.Test;
import play.api.data.Form;
import play.mvc.*;
import scalaz.std.java.util.concurrent.callable;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
}