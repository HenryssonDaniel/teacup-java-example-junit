package io.github.henryssondaniel.teacup.examples.junit.telnet;

import io.github.henryssondaniel.teacup.engine.DefaultSetup;
import io.github.henryssondaniel.teacup.protocol.telnet.client.Factory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An simple setup.
 *
 * @since 1.0
 */
public class SimpleSetup extends DefaultSetup {
  private static final Logger LOGGER = Logger.getLogger(SimpleSetup.class.getName());
  private static final int PORT = 4000;

  @Override
  public void initialize() {
    LOGGER.log(Level.FINE, "Initialize");
    putClient(Constants.TELNET_CLIENT, Factory.createClient("v100"));
    putServer(
        Constants.TELNET_SERVER,
        io.github.henryssondaniel.teacup.protocol.telnet.server.Factory.createSimpleServer(
            io.github.henryssondaniel.teacup.protocol.telnet.server.Factory.createListener(
                100, 1, PORT, 1000000)));
  }
}
