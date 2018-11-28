package exercise10

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import scala.io.StdIn

object Main extends App with Model {

  var users: List[User] = List()

  /**
    * Exercise 10. Create a CRUD service that for users.  Users have an ID and a name
    */
  val routes = ???

  implicit val system = ActorSystem("exercise10")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

trait Model extends DefaultJsonProtocol with SprayJsonSupport {

}
