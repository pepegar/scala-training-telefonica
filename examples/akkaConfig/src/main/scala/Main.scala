package akkaconfig

import akka.actor._
import akka.util.Timeout
import akka.pattern.ask
import akka.routing.FromConfig

import scala.concurrent.duration._

object Main extends App {

  val system = ActorSystem("akkarouting")
  implicit val ec = system.dispatcher
  implicit val timeout = Timeout(5 seconds)
  val master = system.actorOf(FromConfig.props(Worker.props), "worker")

  List.fill(10)(Worker.Sum(3,5)) foreach { msg =>
    (master ? msg) foreach println
  }

  system.terminate()

}