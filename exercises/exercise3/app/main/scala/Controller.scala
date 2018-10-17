package exercise3

import javax.inject._
import play.api._
import play.api.mvc._
import model._
import play.api.libs.json.{JsError, Json, Reads}

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  var users: List[User] = List()

  def listAll() = Action(Ok(Json.toJson(users)))


  def create() = Action(validateAs[User]) { implicit req =>
    users = req.body :: users

    Ok("")
  }

  def read(id: Int) = Action {
    users.find(_.id == id) match {
      case None => NotFound(s"user with id $id not found")
      case Some(x) => Ok(Json.toJson(x))
    }
  }

  def update(Id: Int) = Action(validateAs[User]) { implicit req =>
    val user = req.body

    users = users map {
      case User(Id, name) => user
      case x => x
    }

    Ok("")
  }

  def delete(id: Int) = Action { implicit req =>
    users = users.dropWhile(_.id == id)

    Ok("")
  }


  private def validateAs[A : Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )
}
