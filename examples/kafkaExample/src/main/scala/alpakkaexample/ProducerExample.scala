package alpakkaexample

import java.util.Properties

import org.apache.kafka.clients.producer._

import scala.concurrent.{ExecutionContext, Future}

object ProducerExample {
  def run(implicit ec: ExecutionContext): Future[Unit] = Future {
    val props = new Properties
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(ProducerConfig.ACKS_CONFIG, "all")
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    (0 to 100) foreach { i =>
      val data = new ProducerRecord[String, String]("test-topic", "key-" + i, "message-" + i)
      producer.send(data, callback)
    }
    producer.close()
  }

  val callback: Callback = (recordMetadata: RecordMetadata, e: Exception) => {
    if (e != null) {
      System.out.println("Error while producing message to topic :" + recordMetadata)
      e.printStackTrace()
    }
    else {
      val message = s"sent message to topic:${recordMetadata.topic} partition:${recordMetadata.partition}  offset:${recordMetadata.offset}"
      System.out.println(message)
    }
  }
}