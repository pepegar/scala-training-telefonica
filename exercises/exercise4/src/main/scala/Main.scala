package exercise4

import akka.actor._

object Main extends App {
  /**
    * - create an actor called PongActor that can handle Ping messages, and answers Pong to them
    * - instantiate that actor from this main class
    * - send a Ping message to it
    */

  val system = ActorSystem("exercise4")

  // instantiate and send message to the actor here.

  system.terminate()
}
