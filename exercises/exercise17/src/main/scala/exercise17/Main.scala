package exercise17

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorAttributes, ActorMaterializer, Supervision}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  implicit val sys = ActorSystem("exercise13")
  implicit val mat = ActorMaterializer()

  /**
    * - Create a Summer actor that will handle a Sum(a, b) message and respond with a + b
    * - Create a Source of Sum(a: Int, b: Int)
    * - call the actor from within the flow to convert it to a flow containing the sum
    * - run everything!
    */

  sys.terminate()
}
