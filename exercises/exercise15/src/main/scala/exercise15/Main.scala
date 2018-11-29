package exercise14

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

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

  val source = Source(0 to 5).map(100 / _)
  val result = source.runWith(Sink.fold(0)(_ + _))

  Await.result(result, Duration.Inf)

}
