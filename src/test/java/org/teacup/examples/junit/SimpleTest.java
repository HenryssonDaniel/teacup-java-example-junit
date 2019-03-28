package org.teacup.examples.junit;

import static org.teacup.examples.junit.Constants.HTTP_CLIENT;
import static org.teacup.examples.junit.Constants.HTTP_SERVER;

import io.github.henryssondaniel.teacup.core.Fixture;
import io.github.henryssondaniel.teacup.core.Node;
import io.github.henryssondaniel.teacup.core.assertion.Factory;
import io.github.henryssondaniel.teacup.engine.junit.Teacup;
import io.github.henryssondaniel.teacup.protocol.http.client.Handler;
import io.github.henryssondaniel.teacup.protocol.http.server.Context;
import io.github.henryssondaniel.teacup.protocol.http.server.Simple;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final io.github.henryssondaniel.teacup.protocol.http.client.Simple CLIENT =
      Teacup.getClient(
          io.github.henryssondaniel.teacup.protocol.http.client.Simple.class, HTTP_CLIENT);
  private static final Context CONTEXT =
      io.github.henryssondaniel.teacup.protocol.http.server.Factory.createContextBuilder(
              "/",
              io.github.henryssondaniel.teacup.protocol.http.server.Factory.createResponseBuilder(
                      200, 0L)
                  .setBody()
                  .build())
          .build();
  private static final Handler<String> HANDLER =
      io.github.henryssondaniel.teacup.protocol.http.client.Factory.createHandlerBuilder(
              BodyHandlers.ofString())
          .build();
  private static final HttpRequest HTTP_REQUEST =
      HttpRequest.newBuilder().uri(URI.create("http://localhost:80/test")).build();
  private static final Node<HttpResponse<String>> NODE =
      io.github.henryssondaniel.teacup.protocol.http.node.Factory.<String>createResponseBuilder()
          .setStatusCode(Factory.createIntegerAssert().isEqualTo(200))
          .setVersion(Factory.<Version>createComparableAssert().isSameAs(Version.HTTP_1_1))
          .build();
  private static final Simple SIMPLE = Teacup.getServer(Simple.class, HTTP_SERVER);

  @Test
  void sendAsynchronouslyHttpRequest() throws ExecutionException, InterruptedException {
    var requests = SIMPLE.setContext(CONTEXT);
    NODE.verify(CLIENT.sendAsynchronously(HANDLER, HTTP_REQUEST).get());
    requests.get();
    SIMPLE.removeSupplier(requests);
  }

  @Test
  void sendHttpRequest() throws IOException, InterruptedException {
    var requests = SIMPLE.setContext(CONTEXT);
    NODE.verify(CLIENT.send(HANDLER, HTTP_REQUEST));
    requests.get();
    SIMPLE.removeSupplier(requests);
  }
}
