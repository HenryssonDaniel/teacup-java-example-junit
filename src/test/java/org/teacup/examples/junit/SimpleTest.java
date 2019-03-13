package org.teacup.examples.junit;

import static org.teacup.examples.junit.Constants.HTTP_CLIENT;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.teacup.core.Fixture;
import org.teacup.core.Node;
import org.teacup.core.assertion.Factory;
import org.teacup.engine.junit.Teacup;
import org.teacup.protocol.http.Client;
import org.teacup.protocol.http.Handler;
import org.teacup.protocol.http.HandlerBuilderFactory;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final Client CLIENT = Teacup.getClient(Client.class, HTTP_CLIENT);
  private static final Handler<String> HANDLER =
      HandlerBuilderFactory.create(BodyHandlers.ofString()).build();
  private static final HttpRequest HTTP_REQUEST =
      HttpRequest.newBuilder().uri(URI.create("https://google.com")).build();
  private static final Node<HttpResponse<String>> NODE =
      org.teacup.protocol.http.node.Factory.<String>createResponseBuilder()
          .setStatusCode(Factory.createIntegerAssert().isEqualTo(301))
          .setVersion(Factory.<Version>createComparableAssert().isGreaterThan(Version.HTTP_1_1))
          .build();

  @Test
  void sendAsynchronouslyHttpRequest() throws ExecutionException, InterruptedException {
    NODE.verify(CLIENT.sendAsynchronously(HANDLER, HTTP_REQUEST).get());
  }

  @Test
  void sendHttpRequest() throws IOException, InterruptedException {
    NODE.verify(CLIENT.send(HANDLER, HTTP_REQUEST));
  }
}
