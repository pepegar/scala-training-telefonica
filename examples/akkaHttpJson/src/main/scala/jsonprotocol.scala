package akkahttpjson

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}


object jsonprotocol extends SprayJsonSupport with DefaultJsonProtocol{
  case class Car(brand: String, model: String)
  implicit val carFormat: RootJsonFormat[Car] = jsonFormat2(Car)
}
