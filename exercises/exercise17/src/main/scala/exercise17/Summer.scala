package exercise17

import akka.actor.{Actor, Props}
import exercise17.Summer.Sum

class Summer extends Actor {
  override def receive: Receive = {
    case Sum(a, b) => sender() ! a + b
  }
}

object Summer {

  case class Sum(a: Int, b: Int)

  def props = Props(new Summer)

}