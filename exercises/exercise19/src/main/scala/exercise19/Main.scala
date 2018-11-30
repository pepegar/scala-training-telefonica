package exercise19

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Compression, Framing, Sink, StreamConverters}
import akka.util.ByteString
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  implicit val system = ActorSystem("akka-streams")
  implicit val mat = ActorMaterializer()
  val bootstrapServers = "localhost:9092"

  /**
    * Create a consumer that consumes messages from the random-data topic in kafka and prints them all
    */
  
  system.terminate()
}
