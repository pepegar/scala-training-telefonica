package simple

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action {
    Ok("hello")
  }

}
