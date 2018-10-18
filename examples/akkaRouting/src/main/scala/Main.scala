package akkarouting

import akka.actor._
import akka.routing._

object Main extends App {

  val system = ActorSystem("akkarouting")

  val master = system.actorOf(Master.props)

  master ! Master.Sum(3,2)

  system.terminate()

}
