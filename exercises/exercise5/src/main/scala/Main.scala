package exercise5

import akka.actor._

object Main extends App {
  /**
    * - Modify pong actor so it can handle both ping and pong messages.
    * - Create two instances of it.
    * - make them start pingponging.
    */

  val system = ActorSystem("exercise5")

  // instantiate and send message to the actor here.

  system.terminate()
}
