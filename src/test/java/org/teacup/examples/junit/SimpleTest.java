package org.teacup.examples.junit;

import static org.teacup.examples.junit.Constants.HTTP_CLIENT;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.teacup.core.Fixture;
import org.teacup.engine.junit.Teacup;
import org.teacup.protocols.http.Client;
import org.teacup.protocols.http.HandlerBuilderFactory;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final int MOVED = 301;

  @Test
  void sendHttpRequest() throws ExecutionException, InterruptedException {
    Assertions.assertEquals(
        MOVED,
        Teacup.getClient(Client.class, HTTP_CLIENT)
            .sendAsynchronously(
                HandlerBuilderFactory.create(BodyHandlers.ofString()).build(),
                HttpRequest.newBuilder().uri(URI.create("https://google.com")).build())
            .get()
            .statusCode());
  }
}
