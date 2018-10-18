package akkarouting

import akka.actor.{Actor, Props}

class Worker extends Actor {
  import Master._

  override def receive: Receive = {
    case Sum(x, y) =>  sender() ! x + y
  }
}

object Worker {
  def props: Props = Props(new Worker)
}
