package io.vertx.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx


class HttpVerticle() : AbstractVerticle() {

  override fun start(startFuture: Future<Void>) {
    val port = config().getInteger("http.port")
    vertx
      .createHttpServer()
      .requestHandler { req ->
        vertx.eventBus().publish("message.multicast", "I Love HTTP $port")
        req.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!")
      }
      .listen(port) { http ->
        if (http.succeeded()) {
          vertx.eventBus().publish("message.multicast", "Verticle Started and listening on $port")

          startFuture.complete()
          println("HTTP server started on port $port")
        } else {
          startFuture.fail(http.cause());
        }
      }

  }

}
