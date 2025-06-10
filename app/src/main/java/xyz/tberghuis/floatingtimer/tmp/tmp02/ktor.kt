package xyz.tberghuis.floatingtimer.tmp.tmp02

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun runKtorServer(service: Tmp02Service) {
  embeddedServer(Netty, port = 8080) {
    routing {
      get("/") {
        call.respondText("Hello, world!\n")
      }

      get("/run_test") {
        call.respondText("ok\n")
      }
    }
  }.start(wait = true)
}
