package akkaconfig

import akka.actor.{Actor, ActorRef, Props}

class Worker extends Actor {

  override def receive: Receive = {
    case Worker.Sum(x, y) =>  sender() ! Worker.Response(self, x + y)
  }
}

object Worker {
  case class Sum(x: Int, y: Int)
  case class Response(actorRef: ActorRef, value: Int)
  def props: Props = Props(new Worker)
}
