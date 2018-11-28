package exercise8

import akka.actor.{Actor, Props}

class Bartender extends Actor {

  import Bartender._

  def receive = {
    case DrinkRequest("coffee") =>
      sender() ! Coffee

    case DrinkRequest("water")  =>
      sender() ! Water

    case DrinkRequest("beer") =>
      sender() ! Beer
  }

}

object Bartender {
  def props: Props = Props(new Bartender)

  case class DrinkRequest(drinkName: String)

  sealed trait Drink
  case object Coffee extends Drink
  case object Water extends Drink
  case object Beer extends Drink
}