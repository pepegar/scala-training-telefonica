package exercise6

import akka.actor._
import akka.pattern.ask
import akka.pattern.pipe
import akka.util.Timeout
import exercise6.Counter.{GetCounter, Increment}

import scala.concurrent.duration._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  /**
    * Create a counter actor that accepts two different messages:
    * 
    * - Increment. Increases an internal counter in 1.
    * - GetCounter. Sends the current counter back to the sender.
    * 
    * Instantiate the actor from the main module, and send a couple of
    * increments, then get the counter.
    * 
    * 6.1.  Create a printer actor that accepts a PrintInt method and
    * prints the integer to the console.
    */

  implicit val system = ActorSystem("exercise6")
  implicit val ec = system.dispatcher
  implicit val timeout: Timeout = Timeout(5.seconds)

  val counter = system.actorOf(Counter.props)
  val printer = system.actorOf(Printer.props)

  counter ! Increment
  counter ! Increment

  val response: Future[Any] = counter ? GetCounter

  response.mapTo[Int].map(Printer.PrintInt) pipeTo printer

  system.terminate()
}
