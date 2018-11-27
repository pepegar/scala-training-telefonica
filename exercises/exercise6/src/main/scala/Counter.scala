package exercise6

import akka.actor.{Actor, Props}

class Counter() extends Actor {

  import Counter._

  var counter: Int = 0

  def receive = {
    case Increment => counter = counter + 1
    case GetCounter => sender() ! counter
  }

}

object Counter {

  case object Increment
  case object GetCounter

  def props: Props = Props(new Counter())

}
