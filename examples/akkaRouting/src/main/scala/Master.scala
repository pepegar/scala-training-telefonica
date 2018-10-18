package akkarouting

import akka.actor.{Actor, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

class Master extends Actor {

  import Master._

  var router = Router(RoundRobinRoutingLogic(), Vector.fill(5) {
    val routee = context.actorOf(Worker.props)

    context watch routee

    ActorRefRoutee(routee)
  })

  def receive = {
    case s: Sum =>
      router.route(s, sender())
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Worker.props)
      context watch r
      router = router.addRoutee(r)
  }

}

object Master {
  def props: Props = Props(new Master)

  final case class Sum(a: Int, b: Int)
}
