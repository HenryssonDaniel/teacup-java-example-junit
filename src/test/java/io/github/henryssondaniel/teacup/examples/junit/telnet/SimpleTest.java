package io.github.henryssondaniel.teacup.examples.junit.telnet;

import io.github.henryssondaniel.teacup.engine.Fixture;
import io.github.henryssondaniel.teacup.engine.junit.Teacup;
import io.github.henryssondaniel.teacup.protocol.Server;
import io.github.henryssondaniel.teacup.protocol.telnet.Client;
import io.github.henryssondaniel.teacup.protocol.telnet.server.Context;
import io.github.henryssondaniel.teacup.protocol.telnet.server.Factory;
import io.github.henryssondaniel.teacup.protocol.telnet.server.Request;
import io.github.henryssondaniel.teacup.protocol.telnet.server.Simple;
import java.io.IOException;
import org.junit.jupiter.api.Test;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final int PORT = 4000;
  private static final byte START_BYTE = (byte) 255;

  @Test
  void sendFtpRequest() throws IOException {
    var reply = "reply";

    Server<Context, Request> simpleServer = Teacup.getServer(Simple.class, Constants.TELNET_SERVER);
    var serverSupplier = simpleServer.setContext(Factory.createContext(Factory.createReply(reply)));

    var client = Teacup.getClient(Client.class, Constants.TELNET_CLIENT);
    var clientSupplier = client.connect("localhost", PORT);
    client.send(START_BYTE, (byte) 10);

    io.github.henryssondaniel.teacup.protocol.telnet.node.Factory.createRequestBuilder()
        .setCommandAsString(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createStringAssert()
                .isEqualTo("\n"))
        .build()
        .verify(serverSupplier.get().get(0));

    io.github.henryssondaniel.teacup.protocol.telnet.node.Factory.createResponseBuilder()
        .setDataAsString(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createStringAssert()
                .isEqualTo(reply))
        .build()
        .verify(clientSupplier.get());

    client.disconnect();
    simpleServer.removeSupplier(serverSupplier);
  }
}
