package examples

import akka.actor._

object Main extends App {

  import Room._

  val system = ActorSystem("chat")

  val room = system.actorOf(Room.props("developers"), "developers-room")

  val pepe = system.actorOf(Member.props("pepe"), "pepe")
  val juan = system.actorOf(Member.props("juan"), "juan")
  val antonio = system.actorOf(Member.props("antonio"), "antonio")

  room ! Join(pepe)
  room ! Join(juan)
  room ! Join(antonio)

  room ! Message(pepe, "mensaje de pepe")

  system.terminate()

}
