package exercise8

import akka.actor._
import akka.pattern.ask
import Bartender.DrinkRequest
import akka.routing.{Broadcast, FromConfig}
import akka.util.Timeout

import scala.concurrent.Await
import scala.util.Random
import scala.concurrent.duration._

object Main extends App {

  private val rand = new Random()

  def genRequest = rand.nextInt(3) match {
    case 0 => DrinkRequest("coffee")
    case 1 => DrinkRequest("water")
    case 2 => DrinkRequest("beer")
  }

  /**
    * We've noticed that our bartender cannot keep up with the high number of orders we have.
    *
    * Modify the application.conf file to make instances of the Bartender actor be routed behind a `round-robin-pool` router
    * with a `nr-of-instances` of your choice
    *
    * Exercise 8.1:
    *
    * Modify your Bartender class so it can receive a Tip.
    * Send a tip to all the Bartenders!
    */

  val system = ActorSystem("exercise8")
  implicit val timeout = Timeout(5 seconds)
  implicit val ec = system.dispatcher

  val bartender = system.actorOf(FromConfig.props(Bartender.props), "bartender")

  (bartender ? genRequest).map(x => s"the waiter served $x") foreach println
  (bartender ? genRequest).map(x => s"the waiter served $x") foreach println
  (bartender ? genRequest).map(x => s"the waiter served $x") foreach println
  (bartender ? genRequest).map(x => s"the waiter served $x") foreach println

  bartender ! Broadcast(Bartender.Tip(5))

  Await.result(system.terminate(), Duration.Inf)

}
