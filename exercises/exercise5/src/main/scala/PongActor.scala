package exercise5

import akka.actor.{Actor, Props}

class PongActor extends Actor {

  import PongActor._

  def receive = {
    case Ping => sender() ! Pong
  }

}

object PongActor {
  case object Ping
  case object Pong

  def props = Props(new PongActor)
}
