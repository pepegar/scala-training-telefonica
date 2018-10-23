package exercise8

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
    * Exercise 6. Create a CRUD service that for users.  Users have an ID and a name
    */
  val routes =
    path("/") {
      get {
        complete(users)
      } ~
      put {
        entity(as[User]) { user =>
          users = user :: users

          complete("")
        }
      }
    } ~
    pathPrefix("/", IntNumber) { Id =>
      get {
        complete(users.find(_.id == Id))
      } ~ post {
        entity(as[User]) { user =>
          val IntId = Id.asInstanceOf[Int]
          users = users map {
            case User(IntId, _) => user
            case x => x
          }
          complete("")
        }
      } ~ delete {
        users = users.dropWhile(_.id == Id)
        complete("")
      }
    }


  implicit val system = ActorSystem("exercise-6")
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

  case class User(id: Int, name: String)
  implicit val userFormat: RootJsonFormat[User] = jsonFormat2(User)
}
