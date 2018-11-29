package akkastreams

import java.util.Random

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, OverflowStrategy}
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  implicit val system = ActorSystem("akka-streams")
  implicit val mat = ActorMaterializer()


  val producer: Source[Int, NotUsed] = Source.repeat(5).take(200)
  val flow: Flow[Int, Int, NotUsed] = Flow[Int] map { i =>
    Thread.sleep(10)
    i * i
  }
  val buffer = Flow[Int].buffer(10, OverflowStrategy.backpressure)

  val result = producer.via(buffer).via(flow).runWith(Sink.foreach(println))

  Await.result(result, Duration.Inf)

  system.terminate()
}