package exercise7

import akka.actor.{Actor, ActorRef, Props}
import exercise7.RoomActor._

import scala.collection.mutable

class RoomActor extends Actor {

  val users: mutable.HashMap[String, ActorRef] = mutable.HashMap()

  override def receive: Receive = {
    case AddUser(name, ref) =>
      users += name -> ref
      self ! SendMessage(name, s"user $name joined")
    case SendMessage(username, msg) =>
      users
        .filterKeys(key => key != username)
        .values
        .foreach { ref =>
          ref ! MemberActor.NewMessage(username, msg)
        }

  }
}

object RoomActor {

  case class AddUser(username: String, ref: ActorRef)
  case class SendMessage(username: String, msg: String)

  def props: Props = Props(new RoomActor)

}