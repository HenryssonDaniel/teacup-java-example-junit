package org.teacup.examples.junit;

import static org.teacup.examples.junit.Constants.HTTP_CLIENT;
import static org.teacup.examples.junit.Constants.HTTP_SERVER;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.teacup.core.DefaultSetup;
import org.teacup.protocol.http.server.Factory;

/**
 * An simple setup.
 *
 * @since 1.0
 */
public class SimpleSetup extends DefaultSetup {
  private static final Logger LOGGER = Logger.getLogger(SimpleSetup.class.getName());
  private static final int PORT = 80;

  @Override
  public void initialize() {
    LOGGER.log(Level.FINE, "Initialize");
    putClient(
        HTTP_CLIENT,
        org.teacup.protocol.http.client.Factory.createSimple(HttpClient.newBuilder().build()));

    try {
      putServer(
          HTTP_SERVER,
          Factory.createServer(HttpServer.create(new InetSocketAddress("localhost", PORT), 0)));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
