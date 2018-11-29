package exercise16

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.javadsl.Framing
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  implicit val sys = ActorSystem("exercise13")
  implicit val mat = ActorMaterializer()

  /**
    * change `source` so it uses [[akka.stream.scaladsl.Framing]] instead of
    * framing manually
    */

  val csv =
    """1,Nollie,Brothwell,nbrothwell0@independent.co.uk,Female,131.76.114.60
      |2,Kathy,Moores,kmoores1@admin.ch,Female,66.178.121.219
      |3,Sibyl,Hebbes,shebbes0@yahoo.com,Male,221.222.45.243
      |4,Dasi,Stickley,dstickley4@columbia.edu,Female,125.51.91.181""".stripMargin

  val source: Source[String, NotUsed] = Source
    .single(csv)
    .map(ByteString.apply)
    .via(Framing.delimiter(ByteString("\n"), 600))
    .map(_.utf8String)

  sealed trait Gender
  object Gender {
    case object Male extends Gender
    case object Female extends Gender
    case class Other(name: String) extends Gender

    def fromString(str: String): Gender = str match {
      case "Male" => Male
      case "Female" => Female
      case other => Other(other)
    }
  }

  case class Row(id: String, name: String, surname: String, email: String, gender: Gender, ip: String)

  val parser: Flow[String, Row, NotUsed] = Flow.fromFunction { str =>
    val List(id, name, surname, email, gender, ip) = str.split(",").toList

    Row(id, name, surname, email, Gender.fromString(gender), ip)
  }

  val sink: Sink[Row, Future[Done]] = Sink.foreach(println)

  Await.result(source.via(parser).runWith(sink), Duration.Inf)

  sys.terminate()
}