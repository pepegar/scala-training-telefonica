package exercise17

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorAttributes, ActorMaterializer, Supervision}

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout


object Main extends App {

  implicit val sys = ActorSystem("exercise13")
  implicit val mat = ActorMaterializer()
  implicit val to = Timeout(5 seconds)

  /**
    * - Create a Summer actor that will handle a Sum(a, b) message and respond with a + b
    * - Create a Source of Sum(a: Int, b: Int)
    * - call the actor from within the flow to convert it to a flow containing the sum
    * - run everything!
    */

  val summer = sys.actorOf(Summer.props)

  val futResponse = Source(0 to 100).zip(Source(100 to 0))
    .map(t => Summer.Sum(t._1, t._2))
    .mapAsync(2) { msg =>
      (summer ? msg).mapTo[Int]
    } runWith Sink.foreach(println)

  Await.result(futResponse, Duration.Inf)

  sys.terminate()
}
