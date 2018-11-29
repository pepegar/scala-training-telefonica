package exercise14

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorAttributes, ActorMaterializer, Supervision}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  implicit val sys = ActorSystem("exercise13")
  implicit val mat = ActorMaterializer()

  /**
    * Currently our stream application is failing with a `Future.failed(ArithmeticException)`.
    * Create a new supervision strategy we can use to make it just resume when
    * `ArithmeticExceptions` happen
    *
    *
    */

  val decider: Supervision.Decider = {
    case _: ArithmeticException ⇒ Supervision.Resume
    case _                      ⇒ Supervision.Stop
  }

  val source = Source(0 to 5).map(100 / _).withAttributes(ActorAttributes.supervisionStrategy(decider))
  val result = source.runWith(Sink.fold(0)(_ + _))

  println(Await.result(result, Duration.Inf))

  sys.terminate()
}
