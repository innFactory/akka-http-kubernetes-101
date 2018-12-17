package de.innfactory.k8s101

import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.lightbend.rp.common.SocketBinding

import scala.concurrent.duration._

object HTTPServer extends App {

  implicit val system = ActorSystem("demo")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val backend = system.actorOf(BackendActor.props, BackendActor.name)
  val router = new Router(backend)


  val host = SocketBinding.bindHost("http", default = "127.0.0.1")
  val port = SocketBinding.bindPort("http", default = 8080)

  println(s"HTTP server available at http://$host:$port")
  println(s"Try to get a greeting result with endpoint /greet/:name")

  val bindingFuture =
    Http().bindAndHandle(router.route, host, port)

  CoordinatedShutdown(system).addJvmShutdownHook({
    bindingFuture
      .flatMap(_.unbind())
  })

}
