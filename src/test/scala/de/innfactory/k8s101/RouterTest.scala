package de.innfactory.k8s101

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.Timeout
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

class RouterTest extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val backend = system.actorOf(BackendActor.props, BackendActor.name)
  val router = new Router(backend)
  val routes = router.route

  "HTTP Server" should {

    "respond with ok on base path" in {
      Get() ~> routes ~> check {
        status.isSuccess() shouldEqual true
      }
    }

    "respond a greeting" in {
      Get("/greet/Tobias") ~> routes ~> check {
        status.isSuccess() shouldEqual true
      }
    }

    "respond a greeting with text match " in {
      Get("/greet/Tobias") ~> routes ~> check {
        responseAs[String] shouldEqual BackendActor.messageText("Tobias")
      }
    }

  }

}
