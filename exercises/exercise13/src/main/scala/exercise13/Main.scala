package exercise13

import akka.NotUsed
import akka.stream.scaladsl.Source

object Main extends App {

  /**
    * Create a simple stream application that:
    *
    * - Convert the `csv` string as a `Source[String, NotUsed]`
    * - Create a Row case class containing:
    *   - id
    *   - name
    *   - surname
    *   - email
    *   - gender
    *   - ip
    * - Create a `Flow[String, Row, NotUsed]` that parses each string as a Row
    * - Create a simple Sink that just prints the message it receives
    * - tie everything together and run
    */

  val csv =
    """
      |1,Nollie,Brothwell,nbrothwell0@independent.co.uk,Female,131.76.114.60
      |2,Kathy,Moores,kmoores1@admin.ch,Female,66.178.121.219
      |3,Sibyl,Hebbes,shebbes0@yahoo.com,Female,221.222.45.243
      |4,Dasi,Stickley,dstickley4@columbia.edu,Female,125.51.91.181
    """.stripMargin
}