package vertx;

import io.vertx.core.Vertx;

public class EmbeddedServer {

  public static void main(String[] args) {
    Vertx.vertx().createHttpServer().requestHandler(req -> req.response().end("Hi World!")).listen(8080);
  }
}