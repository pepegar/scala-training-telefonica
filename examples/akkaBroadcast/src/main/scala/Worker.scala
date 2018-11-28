package akkabroadcast

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

class Worker extends Actor with ActorLogging {

  override def receive: Receive = {
    case Worker.Sum(x, y) =>
      log.info(s"$self received a Sum message")
      sender() ! Worker.Response(self, x + y)
  }
}

object Worker {
  case class Sum(x: Int, y: Int)
  case class Response(actorRef: ActorRef, value: Int)
  def props: Props = Props(new Worker)
}
