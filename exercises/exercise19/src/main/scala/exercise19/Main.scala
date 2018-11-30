package exercise19

import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Compression, Framing, Sink, StreamConverters}
import akka.util.ByteString
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  implicit val system = ActorSystem("akka-streams")
  implicit val mat = ActorMaterializer()
  val bootstrapServers = "localhost:9092"

  /**
    * Create a consumer that consumes messages from the random-data topic in kafka and prints them all
    */
  val consumerConf = system.settings.config.getConfig("akka.kafka.consumer")
  val consumerSettings =
    ConsumerSettings(consumerConf, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(bootstrapServers)
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val cons = Consumer
    .plainSource(
      consumerSettings,
      Subscriptions.assignmentWithOffset((new TopicPartition("random-data", 0), 0))
    ).runWith(Sink.foreach(println))

  Await.result(cons, Duration.Inf)

  system.terminate()
}
