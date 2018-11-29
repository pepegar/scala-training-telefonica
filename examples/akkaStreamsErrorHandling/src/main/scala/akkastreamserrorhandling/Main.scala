package akkastreamserrorhandling

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  implicit val system = ActorSystem("akka-streams")
  implicit val mat = ActorMaterializer()

  val result1 = Source(-5 to 5)
    .map(1 / _) //throwing ArithmeticException: / by zero
    .log("error logging")
    .runWith(Sink.ignore)

  val result2 = Source(0 to 6).map(n ⇒
    if (n < 5) n.toString
    else throw new RuntimeException("Boom!")
  ).recover {
    case _: RuntimeException ⇒ "stream truncated"
  }.runForeach(println)
  Await.result(result1.zip(result2), Duration.Inf)

  system.terminate()
}