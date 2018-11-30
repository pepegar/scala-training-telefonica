package alpakkaexample

import java.util.Properties

import scala.concurrent.{ExecutionContext, Future}

object ConsumerExample {

  import java.util
  import java.util.Collections

  import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRebalanceListener, KafkaConsumer}
  import org.apache.kafka.common.TopicPartition

  def run()(implicit executionContext: ExecutionContext): Future[Unit] = Future {
    val consumerConfig = new Properties()
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group")
    consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[Array[Byte], Array[Byte]](consumerConfig)
    consumer.subscribe(Collections.singletonList("test-topic"), rebalanceListener)
    while ( {
      true
    }) {
      val records = consumer.poll(1000)
      import scala.collection.JavaConversions._
      for (record <- records) {
        println(s"Received Message ${record.value} in topic ${record.topic}")
      }
      consumer.commitSync()
    }
  }

  val rebalanceListener: ConsumerRebalanceListener = new ConsumerRebalanceListener {
    override def onPartitionsRevoked(partitions: util.Collection[TopicPartition]): Unit = {
      System.out.println("Called onPartitionsRevoked with partitions:" + partitions)
    }

    override def onPartitionsAssigned(partitions: util.Collection[TopicPartition]): Unit = {
      System.out.println("Called onPartitionsAssigned with partitions:" + partitions)
    }
  }
}