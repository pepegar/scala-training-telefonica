package playtesting

import javax.inject._
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton class Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action {
    Ok("OK")
  }

}
