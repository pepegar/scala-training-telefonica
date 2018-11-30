package alpakkaexample

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Main extends App {

  val exec = for {
    _ <- ProducerExample.run
    _ <- ConsumerExample.run
  } yield ()

  Await.result(exec, Duration.Inf)
}
