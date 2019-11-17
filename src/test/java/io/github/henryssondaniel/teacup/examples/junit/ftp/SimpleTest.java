package io.github.henryssondaniel.teacup.examples.junit.ftp;

import io.github.henryssondaniel.teacup.engine.Fixture;
import io.github.henryssondaniel.teacup.engine.junit.Teacup;
import io.github.henryssondaniel.teacup.protocol.Node;
import io.github.henryssondaniel.teacup.protocol.Server;
import io.github.henryssondaniel.teacup.protocol.ftp.Client;
import io.github.henryssondaniel.teacup.protocol.ftp.client.Command;
import io.github.henryssondaniel.teacup.protocol.ftp.client.Response;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Context;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Factory;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Request;
import io.github.henryssondaniel.teacup.protocol.ftp.server.Simple;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;

@Fixture(SimpleSetup.class)
class SimpleTest {
  private static final String ARGUMENT = "argument";
  private static final int CODE = 200;
  private static final String MESSAGE = "message";
  private static final int PORT = 1234;

  @Test
  void sendFtpRequest() throws IOException {
    Server<Context, Request> simpleServer = Teacup.getServer(Simple.class, Constants.FTP_SERVER);

    var requests =
        simpleServer.setContext(
            Factory.createContextBuilder(Factory.createReplyBuilder(CODE, MESSAGE).build())
                .build());

    var client = Teacup.getClient(Client.class, Constants.FTP_CLIENT);
    client.connect("localhost", PORT);

    createResponse().verify(client.send(Command.ACCT, ARGUMENT));
    verifyRequests(requests.get());

    simpleServer.removeSupplier(requests);
  }

  private static Node<Response> createResponse() {
    return io.github.henryssondaniel.teacup.protocol.ftp.node.Factory.createResponseBuilder()
        .setCode(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createIntegerAssert()
                .isEqualTo(CODE))
        .setText(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createStringAssert()
                .isEqualTo(CODE + " " + MESSAGE + "\r\n"))
        .build();
  }

  private static void verifyRequests(List<? extends Request> requests) {
    io.github.henryssondaniel.teacup.core.assertion.Factory.createListAssert()
        .hasSize(1)
        .verify(requests);

    io.github.henryssondaniel.teacup.protocol.ftp.node.Factory.createRequestBuilder()
        .setArgument(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createStringAssert()
                .isEqualTo(ARGUMENT))
        .setCommand(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createStringAssert()
                .isEqualTo("ACCT"))
        .setReceivedTime(
            io.github.henryssondaniel.teacup.core.assertion.Factory.createLongAssert()
                .isLessThanOrEqualTo(Date.from(Instant.now()).getTime()))
        .build()
        .verify(requests.get(0));
  }
}
