package akkastreamsexample

import java.io.{File, FileInputStream, InputStream}
import java.net.InetAddress
import java.nio.file.{Path, Paths}

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.Future

object Main extends App {

  implicit val system = ActorSystem("akka-http-routing")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val parallelism = 4

  val files: List[File] = Paths.get(getClass.getResource("files").toURI).toFile.listFiles.toList

  case class Row(id: Int, firstName: String, lastName: String, email:String, gender: String, ipAddress: InetAddress, debt: Float)
  case class Avg(avg: Int)

  /**
    * [[Framing]] object is used here to split the contents of the CSV file into lines.
    */
  val lineDelimiter: Flow[ByteString, ByteString, NotUsed] =
    Framing.delimiter(ByteString("\n"), 600, true)

  val fields: Flow[ByteString, Row, NotUsed] = Flow[ByteString].mapAsync(parallelism) { str: ByteString =>
    Future {
      val f = str.utf8String.split(",")

      Row(f(0).toInt, f(1), f(2), f(3), f(4), InetAddress.getByName(f(5)), f(6).substring(1).toFloat)
    }
  }

  val parseFile: Flow[File, Row, NotUsed] =
    Flow[File].flatMapConcat { file =>
      val fis: InputStream = new FileInputStream(file)

      StreamConverters
        .fromInputStream(() => fis)
        .via(lineDelimiter)
        .via(fields)
    }


  parseFile.runWith(Source(files), Sink.foreach(println))



}
