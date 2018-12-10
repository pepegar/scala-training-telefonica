package exercise14

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, ClosedShape}
import akka.{Done, NotUsed}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, Merge, RunnableGraph, Sink, Source}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main extends App {

  implicit val sys = ActorSystem("exercise13")
  implicit val mat = ActorMaterializer()

  /**
    * Create a graph topology that:
    *
    * - Convert the `csv` string as a `Source[String, NotUsed]`
    * - Create a Row case class containing:
    *   - id
    *   - name
    *   - surname
    *   - email
    *   - gender
    *   - ip
    * - Create a parser `Flow[String, Row, NotUsed]` that parses each string as a Row
    * - Creates three different `Flow[Row, Row, NotUsed]`, one that handles males, other for females and other for others.
    * - Broadcasts the parser the different gender parsers
    * - Create a simple Sink that just prints the message it receives
    * - tie everything together and run
    */

  val csv =
    """1,Nollie,Brothwell,nbrothwell0@independent.co.uk,Female,131.76.114.60
      |2,Kathy,Moores,kmoores1@admin.ch,Female,66.178.121.219
      |2,Kerry,Monalisa,kmoores1@admin.ch,Potato,66.178.233.219
      |3,Sibyl,Hebbes,shebbes0@yahoo.com,Male,221.222.45.243
      |4,Dasi,Stickley,dstickley4@columbia.edu,Female,125.51.91.181""".stripMargin

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

  val graph = RunnableGraph.fromGraph(GraphDSL.create() {
    implicit builder : GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      import Gender._
      val source: Source[String, NotUsed] = Source(csv.split("\n").toList)

      val parser: Flow[String, Row, NotUsed] = Flow.fromFunction { str =>
        val List(id, name, surname, email, gender, ip) = str.split(",").toList

        Row(id, name, surname, email, Gender.fromString(gender), ip)
      }

      val sink: Sink[Row, Future[Done]] = Sink.foreach(println)
      val broadcast = builder.add(Broadcast[Row](3))
      val merge = builder.add(Merge[Row](3))

      def genFlow(predicate: Gender => Boolean): Flow[Row, Row, NotUsed] =
        Flow[Row].filter(row => predicate(row.gender))

      val males = genFlow(_ == Male)
      val females = genFlow(_ == Female)
      val others = genFlow(_.isInstanceOf[Other])

      source ~> parser ~> broadcast ~> males   ~> merge ~> sink
                          broadcast ~> females ~> merge
                          broadcast ~> others  ~> merge

      ClosedShape
  })

  graph.run()
  sys.terminate()
}
