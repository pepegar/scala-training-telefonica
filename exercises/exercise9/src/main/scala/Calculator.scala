package exercise9

import akka.actor.{Actor, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

class Calculator extends Actor {
  override def receive: Receive = {
    case Calculator.Sum(a, b) => sender() ! Calculator.Response(a + b)
    case Calculator.Mult(a, b) => sender() ! Calculator.Response(a * b)
    case Calculator.Div(a, b) => sender() ! Calculator.Response(a / b)
  }
}

object Calculator extends DefaultJsonProtocol with SprayJsonSupport {

  def props: Props = Props(new Calculator)
  
  case class Response(i: Int)
  case class Sum(a: Int, b: Int)
  case class Mult(a: Int, b: Int)
  case class Div(a: Int, b: Int)

  implicit val responseFormat: RootJsonFormat[Response] = jsonFormat1(Response)
}
