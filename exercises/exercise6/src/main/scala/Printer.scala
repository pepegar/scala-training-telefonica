package exercise6

import akka.actor.{Actor, Props}

class Printer() extends Actor {

  import Printer._


  def receive = {
    case PrintInt(i) => println(s"received $i")
  }

}


object Printer {

  case class PrintInt(i: Int)

  def props: Props = Props(new Printer())

}