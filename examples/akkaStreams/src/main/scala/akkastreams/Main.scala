package akkastreams

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  implicit val system = ActorSystem("akka-streams")
  implicit val mat = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)
  val flow: Flow[Int, Int, NotUsed] = Flow.fromFunction(_ * 2)
  val sink: Sink[Int, Future[Done]] = Sink.foreach(println)

  val a: Future[Done] = source.via(flow).runWith(sink)

  Await.result(a, Duration.Inf)

  system.terminate()
}