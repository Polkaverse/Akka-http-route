package com.knoldus

import scala.io.StdIn

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

/**
 * Created by manjot on 7/6/18.
 */
class RestServer(implicit val system:ActorSystem,
    implicit  val materializer:ActorMaterializer) extends RestServiceImpl {
  def startServer(address:String, port:Int) = {
    implicit val executionContext = system.dispatcher
    val bindingFuture = Http().bindAndHandle(route,address,port)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return

    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}

object RestServer {

  def main(args: Array[String]) {

    implicit val actorSystem = ActorSystem("rest-server")
    implicit val materializer = ActorMaterializer()

    val server = new RestServer()
    server.startServer("localhost",8088)
  }
}
