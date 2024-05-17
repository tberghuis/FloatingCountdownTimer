package xyz.tberghuis.floatingtimer

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

val globalKtorClient = HttpClient(OkHttp) {
//  install(Logging) {
//    logger = Logger.DEFAULT
//    level = LogLevel.ALL
//  }
  install(ContentNegotiation) {
    json()
  }
}