package de.innfactory.k8s101

import akka.actor.ActorRef
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers
import akka.pattern._
import akka.util.Timeout
import de.innfactory.k8s101.BackendActor.{Answer, Greet}

import scala.concurrent.{ExecutionContext, Future}

class Router(backend: ActorRef)(implicit executionContext: ExecutionContext, timeout: Timeout) {

  val route = (get & pathEndOrSingleSlash) {
    complete("OK")
  }  ~
    pathPrefix("greet") {
      path(PathMatchers.Segment) { name =>
        get {
          complete({
            (backend ? Greet(name)).asInstanceOf[Future[Answer]].map { result =>
              HttpEntity(ContentTypes.`text/html(UTF-8)`, result.message)
            }
          })
        }
      }
    }

}