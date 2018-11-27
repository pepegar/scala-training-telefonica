package exercise6

import akka.actor._

object Main extends App {

  /**
    * Create a counter actor that accepts two different messages:
    * 
    * - Increment. Increases an internal counter in 1.
    * - GetCounter. Sends the current counter back to the sender.
    * 
    * Instantiate the actor from the main module, and send a couple of
    * increments, then get the counter.
    */

  val system = ActorSystem("exercise6")

  system.terminate()
}
