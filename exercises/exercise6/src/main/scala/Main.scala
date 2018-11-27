package exercise6

import akka.actor._
import akka.pattern.ask
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
    */

  implicit val system = ActorSystem("exercise6")
  implicit val timeout: Timeout = Timeout(5.seconds)

  val counter = system.actorOf(Counter.props)

  counter ! Increment
  counter ! Increment

  val response: Future[Any] = counter ? GetCounter

  // Don't do this at home, we're blocking the main thread
  print("the response was " + Await.result(response, Duration.Inf))

  system.terminate()
}
