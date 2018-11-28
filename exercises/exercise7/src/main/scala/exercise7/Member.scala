package exercise7

import akka.actor._

class Member(name: String) extends Actor with ActorLogging {

  import Member._

  def receive = {
    case NewMessage(member, msg) =>
      log.info(s"$name received message `$msg` from $member")
  }

}

object Member {
  case class NewMessage(from: ActorRef, msg: String)

  def props(name: String) = Props(new Member(name))
}
