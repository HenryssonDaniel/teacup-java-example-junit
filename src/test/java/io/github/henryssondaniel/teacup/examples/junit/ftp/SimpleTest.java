package io.github.henryssondaniel.teacup.examples.junit.ftp;

import io.github.henryssondaniel.teacup.core.Fixture;
import io.github.henryssondaniel.teacup.engine.junit.Teacup;
import io.github.henryssondaniel.teacup.protocol.ftp.Client;
import io.github.henryssondaniel.teacup.protocol.ftp.SimpleServer;
import io.github.henryssondaniel.teacup.protocol.ftp.client.Command;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Factory;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Reply;
import java.io.IOException;
import org.junit.jupiter.api.Test;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final int PORT = 1234;
  private static final SimpleServer SIMPLE_SERVER =
      Teacup.getServer(SimpleServer.class, Constants.FTP_SERVER);

  @Test
  void sendHttpRequest() throws IOException {
    var requests = SIMPLE_SERVER.setContext(Factory.createContextBuilder(new TestReply()).build());
    var client = Teacup.getClient(Client.class, Constants.FTP_CLIENT);
    client.connect("localhost", PORT);
    client.send(Command.ACCT, "argument");
    requests.get();
    SIMPLE_SERVER.removeSupplier(requests);
  }

  private static class TestReply implements Reply {
    @Override
    public int getCode() {
      return 200;
    }

    @Override
    public String getMessage() {
      return "message";
    }
  }
}
