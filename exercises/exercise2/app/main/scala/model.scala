package exercise2

import play.api.libs.json.{Json, Reads}

object model {

  case class Car(brand: String, model: String)

  implicit val carReads: Reads[Car] = Json.reads[Car]

}
