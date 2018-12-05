package excercise6

import akka.actor.{Actor, Props}
import excercise6.PrinterActor.PrintInt

class PrinterActor extends Actor {

  def receive: Receive = {
    case PrintInt(i) => println(s"$i recibido")
  }

}

object PrinterActor {

  case class PrintInt(i: Int)

  def props: Props = Props(new PrinterActor)

}
