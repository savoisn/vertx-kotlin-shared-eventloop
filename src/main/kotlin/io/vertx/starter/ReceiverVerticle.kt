package io.vertx.starter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.eventbus.Message



class ReceiverVerticle : AbstractVerticle() {
  override fun start(startFuture: Future<Void>) {
    val port = config().getInteger("http.port")
    val eventBus = vertx.eventBus()
    eventBus.consumer("message.multicast")
    { receivedMessage:Message<String> -> println("Received message on port $port : ${receivedMessage.body()}")}

  }
}
