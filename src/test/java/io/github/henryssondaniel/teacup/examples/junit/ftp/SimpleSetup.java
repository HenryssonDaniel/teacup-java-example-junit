package io.github.henryssondaniel.teacup.examples.junit.ftp;

import io.github.henryssondaniel.teacup.engine.DefaultSetup;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Factory;
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
        Constants.FTP_CLIENT,
        io.github.henryssondaniel.teacup.protocol.ftp.client.Factory.createClient());

    putServer(
        Constants.FTP_SERVER,
        Factory.createServer(
            Factory.createConfigurationBuilder()
                .setPort(PORT)
                .setServerAddress("localhost")
                .build()));
  }
}
