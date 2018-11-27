package exercise9

import akka.actor.{ActorRef, ActorSystem}
import akka.util.Timeout
import akka.pattern.ask
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol
import scala.concurrent.duration._

import scala.io.StdIn

object Main extends App with Model {

  import Calculator._

  /**
    * Create an HTTP endpont for a calculator actor.  The calculator actor should be able to sum, multiply & divide two numbers.
    */

  implicit val system = ActorSystem("exercise-7")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  implicit val timeout: Timeout = Timeout(5 seconds)

  val calculator: ActorRef = system.actorOf(Calculator.props)

  val routes =
    path("/sum" / IntNumber / IntNumber) { (a, b)  =>
      complete((calculator ? Sum(a, b)).mapTo[Response])
    } ~ path("/mult" / IntNumber / IntNumber) { (a, b)  =>
      complete((calculator ? Mult(a, b)).mapTo[Response])
    } ~ path("/div" / IntNumber / IntNumber) { (a, b)  =>
      complete((calculator ? Div(a, b)).mapTo[Response])
    }

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

trait Model extends DefaultJsonProtocol with SprayJsonSupport {

}
