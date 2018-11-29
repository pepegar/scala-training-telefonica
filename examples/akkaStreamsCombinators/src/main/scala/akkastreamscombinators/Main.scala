package akkastreamscombinators

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Compression, Framing, Sink, StreamConverters}
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  implicit val system = ActorSystem("akka-streams")
  implicit val mat = ActorMaterializer()

  val is = getClass.getResourceAsStream("/data.csv.gz")

  val flow = StreamConverters.fromInputStream(() => is)
    .via(Compression.gunzip())
    .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 300))
    .map(x => s"Line(${x.utf8String})")
    .runWith(Sink.foreach(println))

  Await.result(flow, Duration.Inf)

  system.terminate()
}