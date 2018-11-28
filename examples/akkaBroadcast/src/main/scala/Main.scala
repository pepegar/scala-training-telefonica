package akkabroadcast

import akka.actor._
import akka.routing.{Broadcast, FromConfig}


object Main extends App {

  val system = ActorSystem("akkarouting")
  implicit val ec = system.dispatcher
  val master = system.actorOf(FromConfig.props(Worker.props), "worker")

  master ! Broadcast(Worker.Sum(3,5))

  system.terminate()

}