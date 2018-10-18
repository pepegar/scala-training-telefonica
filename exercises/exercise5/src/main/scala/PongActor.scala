package exercise5

import akka.actor.{Actor, ActorRef, ActorLogging, Props}

class PingPongActor extends Actor with ActorLogging {

  import PingPongActor._

  def receive = {
    case Ping =>
      Thread.sleep(100)
      val s = sender()
      log.info(s"ping received, sending pong to $s")
      s ! Pong
    case Pong =>
      Thread.sleep(100)
      val s = sender()
      log.info(s"pong received, sending ping to $s")
      s ! Ping
    case Start(other, msg) =>
      other ! msg
  }

}

object PingPongActor {
  case object Ping
  case object Pong
  case class Start(other: ActorRef, msg: Any)

  def props = Props(new PingPongActor)
}
