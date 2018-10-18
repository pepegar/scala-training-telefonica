package exercise5

import akka.actor._

object Main extends App {
  /**
    * - Modify pong actor so it can handle both ping and pong messages.
    * - Create two instances of it.
    * - make them start pingponging.
    */

  import PingPongActor._

  val system = ActorSystem("exercise5")

  val ping = system.actorOf(PingPongActor.props, "ping")
  val pong = system.actorOf(PingPongActor.props, "pong")

  ping ! Start(pong, Ping)
}
