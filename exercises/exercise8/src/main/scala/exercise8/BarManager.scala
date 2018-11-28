package exercise8

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props}
import akka.routing.{ActorRefRoutee, RoundRobinPool, RoundRobinRoutingLogic, Router}

class BarManager extends Actor with ActorLogging {

  var router = Router(RoundRobinRoutingLogic(), Vector.fill(5) {
    val routee = context.actorOf(Bartender.props)

    context watch routee

    ActorRefRoutee(routee)
  })


  override val supervisorStrategy = OneForOneStrategy() {
    case d : Bartender.DrinkNotSupported =>  Restart
    case _ => Escalate
  }

  override def receive: Receive = {
    case x => router.route(x, sender())
  }
}

object BarManager {

  def props: Props = Props(new BarManager)
}