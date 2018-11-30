package alpakkaexample

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.kafka.scaladsl.{Consumer, Producer}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer, StringSerializer}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  implicit val system = ActorSystem("alpakkaexample", config = ConfigFactory.load())
  implicit val mat = ActorMaterializer()

  val bootstrapServers = "localhost:9092"
  val topic = "test-topic"

  val producerConf = system.settings.config.getConfig("akka.kafka.producer")
  val producerSettings =
    ProducerSettings(producerConf, new StringSerializer, new StringSerializer)
      .withBootstrapServers(bootstrapServers)
  val consumerConf = system.settings.config.getConfig("akka.kafka.consumer")
  val consumerSettings =
    ConsumerSettings(consumerConf, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(bootstrapServers)
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val producer: Future[Done] =
    Source(1 to 100)
      .map(_.toString)
      .map(value => new ProducerRecord[String, String](topic, value))
      .runWith(Producer.plainSink(producerSettings))

  val cons = Consumer
    .plainSource(
      consumerSettings,
      Subscriptions.assignment(new TopicPartition(topic, 0))
    ).runWith(Sink.foreach(println))

  Await.result(producer.zip(cons), Duration.Inf)

  system.terminate()

}
