package io.github.henryssondaniel.teacup.examples.junit;

import com.sun.net.httpserver.HttpServer;
import io.github.henryssondaniel.teacup.core.DefaultSetup;
import io.github.henryssondaniel.teacup.protocol.http.server.Factory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An simple setup.
 *
 * @since 1.0
 */
public class SimpleSetup extends DefaultSetup {
  private static final Logger LOGGER = Logger.getLogger(SimpleSetup.class.getName());
  private static final int PORT = 1234;

  @Override
  public void initialize() {
    LOGGER.log(Level.FINE, "Initialize");
    putClient(
        Constants.HTTP_CLIENT,
        io.github.henryssondaniel.teacup.protocol.http.client.Factory.createSimple(
            HttpClient.newBuilder().build()));

    try {
      putServer(
          Constants.HTTP_SERVER,
          Factory.createServer(HttpServer.create(new InetSocketAddress("localhost", PORT), 0)));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
