package io.vertx.starter

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.json.JsonObject
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager


fun main(args : Array<String>) {
  println("Hello, world!")
  val port = Integer(args[0]).toInt()
  val mgr = HazelcastClusterManager()
  val options = VertxOptions().setClusterManager(mgr)
  Vertx.clusteredVertx(options) { res ->
    if (res.succeeded()) {
      println("res ok shop")
      val vertx = res.result()
      staticMode(vertx, port)
    } else {
      println("FAIL !!!")
    }
  }
  println("End of Shop Launcher")
}

fun staticMode(vertx: Vertx, port: Int) {
  val options = DeploymentOptions()
    .setConfig(JsonObject().put("http.port", port)
    )

  vertx.deployVerticle(HttpVerticle::class.java.name, options)
  vertx.deployVerticle(ReceiverVerticle::class.java.name, options)
}


