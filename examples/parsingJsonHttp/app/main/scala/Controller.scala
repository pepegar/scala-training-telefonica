package parsingJson

import javax.inject._
import play.api.libs.json.{JsError, Json, Reads, Writes}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  case class Car(brand: String, model: String)
  implicit val readsCar: Reads[Car] = Json.reads[Car]
  implicit val writesCar: Writes[Car] = Json.writes[Car]

  def validateAs[A : Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def index() = Action(validateAs[Car]) { implicit req =>
    val car : Car = req.body

    Ok(Json.toJson(car))
  }

  def other() =  Action { implicit req =>
    Json.fromJson[Car](req.body.asJson.get).asEither match {
      case Left(err) => BadRequest(JsError.toJson(err))
      case Right(car) =>
        // Do stuff with the car
        Ok(Json.toJson(car))
    }
  }
}
