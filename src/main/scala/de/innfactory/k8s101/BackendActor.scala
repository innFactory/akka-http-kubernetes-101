package de.innfactory.k8s101

import akka.actor.{Actor, Props}
import de.innfactory.k8s101.BackendActor.{Answer, Greet}



object BackendActor {

  case class Greet(name: String)

  case class Answer(message: String)

  def props = Props(new BackendActor)
  def name = "backendActor"

  def messageText(name: String) = s"Hallo $name, this is the kubernetes 101 response!"
}


class BackendActor extends Actor {


  override def receive: Receive = {
    case greet: Greet => sender ! Answer(BackendActor.messageText(greet.name))
  }

}
