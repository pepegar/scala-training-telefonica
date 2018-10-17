package dbaccess

import javax.inject._
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Controller @Inject()(ws: WSClient, cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit req =>

    val req = ws.url("asdf")
      .withMethod("POST")
      .withBody(Json.toJson("hello dolly"))


    Ok("")
  }

}
