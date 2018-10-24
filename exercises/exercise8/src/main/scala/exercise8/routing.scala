package exercise8

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object routing extends Model {

  var users: List[User] = List()

  /**
    * Exercise 6. Create a CRUD service that for users.  Users have an ID  and a name
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
}

trait Model extends DefaultJsonProtocol with SprayJsonSupport {

  case class User(id: Int, name: String)
  implicit val userFormat: RootJsonFormat[User] = jsonFormat2(User)
}
