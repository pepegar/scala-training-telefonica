package exercise3

import play.api.libs.json.{Json, Reads, Writes}

object model {

  case class User(id: Int, name: String)

  implicit val readsUser: Reads[User] = Json.reads[User]
  implicit val writesUser: Writes[User] = Json.writes[User]
}
