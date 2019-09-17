package io.github.henryssondaniel.teacup.examples.junit.telnet;

import io.github.henryssondaniel.teacup.core.Fixture;
import io.github.henryssondaniel.teacup.engine.junit.Teacup;
import io.github.henryssondaniel.teacup.protocol.telnet.Client;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final int PORT = 4000;

  @Test
  void sendFtpRequest() throws IOException {
    var client = Teacup.getClient(Client.class, Constants.TELNET_CLIENT);

    var supplier = client.connect("aardmud.org", PORT);
    client.send("Username");
    Assertions.assertTrue(supplier.get().startsWith("####################"));

    client.disconnect();
  }
}
