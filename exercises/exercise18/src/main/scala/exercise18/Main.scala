package exercise18

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
  val producerConf = system.settings.config.getConfig("akka.kafka.producer")
  val producerSettings =
    ProducerSettings(producerConf, new StringSerializer, new StringSerializer)
      .withBootstrapServers(bootstrapServers)
  /**
    * Create a producer (represented as a Sink in alpakka-kafka) and make it send
    * all the records to the random-data topic in kafka
    */
  val is = getClass.getResourceAsStream("/data.csv.gz")

  val flow = StreamConverters.fromInputStream(() => is)
    .via(Compression.gunzip())
    .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 300))
    .map(_.utf8String)
    .map(value => new ProducerRecord[String, String]("random-data", value))
    .runWith(Producer.plainSink(producerSettings))

  Await.result(flow, Duration.Inf)

  system.terminate()
}