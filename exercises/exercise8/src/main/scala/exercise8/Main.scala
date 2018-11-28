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
    *
    * Exercise 8.2:
    *
    * Introduce a new actor BarManager that will supervise all Bartenders.  The actor should have a supervision strategy that:
    * - handles DrinkNotSupportedExceptions by restarting the actor
    * - escalates all other ones
    */

  val system = ActorSystem("exercise8")
  implicit val timeout = Timeout(5 seconds)
  implicit val ec = system.dispatcher

  val manager = system.actorOf(BarManager.props, "manager")

  (manager ? genRequest).map(x => s"the waiter served $x") foreach println
  (manager ? genRequest).map(x => s"the waiter served $x") foreach println
  (manager ? genRequest).map(x => s"the waiter served $x") foreach println
  (manager ? genRequest).map(x => s"the waiter served $x") foreach println
  (manager ? DrinkRequest("Ginger ale")).map(x => s"the waiter served $x") foreach println

  Await.result(system.terminate(), Duration.Inf)

}
