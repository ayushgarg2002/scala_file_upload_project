package com.knoldus.bootstrap


import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.knoldus.models.{Handlers, Routes}
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.global
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration._
import scala.io.StdIn



object Application extends App {

  val conf = ConfigFactory.load()
  val httpConf = conf.getConfig("http")

  implicit val actorSystem: ActorSystem = ActorSystem("actor-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()(actorSystem)
  implicit val logger: LoggingAdapter = Logging(actorSystem, "Akka-File-Upload")
  implicit val executionContext: ExecutionContextExecutor = global

  try {
    val hostname: String = httpConf.getString("hostname")
    val port: Int = httpConf.getInt("port")

    val handlers: Handlers = new Handlers(conf)
    val routes: Routes = new Routes(handlers)

    val serverRoutes: Route = routes.routes
    val serverBinding = Http().bindAndHandle(serverRoutes, hostname, port)
    println(s"Server online at http://$hostname:$port/\nPress RETURN to stop...")
    StdIn.readLine()
    serverBinding
      .flatMap(_.unbind())
      .onComplete(_ => actorSystem.terminate())
  } catch {
    case e: Throwable =>
      logger.error(e, "Error starting application:")
      Await.result(actorSystem.terminate(), 30.seconds)
  }

}
