package exercise7

import akka.actor.{Actor, Props}
import exercise7.MemberActor.NewMessage

class MemberActor(username: String) extends Actor {

  var dnd = false

  def receive: Receive =  {
    case NewMessage(sendername, msg) =>
      println(s"[$username] $sendername says: $msg")
  }
}

object MemberActor {

  case class NewMessage(username: String, msg: String)

  def props(username: String): Props =
    Props(new MemberActor(username))

}
