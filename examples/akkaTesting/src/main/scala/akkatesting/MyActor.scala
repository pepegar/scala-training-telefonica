package examples

import akka.actor._
import examples.MyActor.{Hello, World}

class MyActor extends Actor {
  override def receive: Receive = {
    case Hello => sender() ! World
  }
}

object MyActor {
  case object Hello
  case object World

  def props = Props(new MyActor)
}