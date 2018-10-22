package examples

import scala.collection.mutable
import akka.actor._

class Room(name: String) extends Actor with ActorLogging {

  import Room._
  import Member._

  var members: mutable.ListBuffer[ActorRef] = mutable.ListBuffer()

  def receive = {
    case Join(ref) =>
      log.info(s"$ref joined")
      members += ref
    case Message(member, msg) =>
      members.foreach { member =>
        member ! NewMessage(member, msg)
      }
  }

}

object Room {

  case class Join(ref: ActorRef)
  case class Message(member: ActorRef, msg: String)

  def props(name: String) = Props(new Room(name))

}
