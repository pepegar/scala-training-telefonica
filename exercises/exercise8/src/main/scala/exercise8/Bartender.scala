package exercise8

import akka.actor.{Actor, ActorLogging, Props}

class Bartender extends Actor with ActorLogging {

  import Bartender._

  def receive = {
    case Tip(n) =>
      log.info(s"$self: Thanks for the $n bucks!")

    case DrinkRequest("coffee") =>
      sender() ! Coffee

    case DrinkRequest("water")  =>
      sender() ! Water

    case DrinkRequest("beer") =>
      sender() ! Beer

    case DrinkRequest(other) =>
      log.error(s"we are a decent bar, we don't serve $other")

      throw new DrinkNotSupported(other)
  }

}

object Bartender {
  def props: Props = Props(new Bartender)

  class DrinkNotSupported(name: String) extends Exception(s"$name not supported")

  case class DrinkRequest(drinkName: String)
  case class Tip(amount: Int)

  sealed trait Drink
  case object Coffee extends Drink
  case object Water extends Drink
  case object Beer extends Drink
}