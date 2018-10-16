# Play framework

##

- Web app framework
- MVC
- Reactive (thanks to Akka & Akka streams)

## In this session

- Controllers
- Routes
- Json
- Persistence
- HTTP client

# Controller

##

As an MVC framework Play uses controllers to handle requests.

##

In play, your controllers need to extend `AbstractController`

##

```scala
@Singleton
class Controller @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

}
```

## Actions

Actions are how you handle requests in the controller. `Action` is
basically a function `Request[A] => Response`.

## Actions

```scala
def index() = Action {
  Ok("hello")
}
```

## Example

Tying controllers and Actions together

# Routes

##

In Play, you describe your webapp endpoints as routes:

##

```
# https://www.playframework.com/documentation/latest/ScalaRouting
# ~~~~

# An example controller showing a sample home page
GET     /                           package.Controller.index

PUT     /:id                        package.Controller.putStuff(id)

DELETE  /:id                        package.Controller.deleteItem(id)
```

##

All accepted verbs for routes are `GET`,`POST`,`PUT`,`DELETE`

## Exercise 2

Create a new route that receives a `name` in the URL and prints `"hello $name"`


# JSON

##

Play represents JSON data as an ADT.

##

1. a String
2. a Boolean
3. A number
4. ~null~
5. an array of JSON values
6. an object of String to JSON values.

##

```scala
sealed trait JsValue
case class JsString(str: String) extends JsValue
case class JsBoolean(bool: Boolean) extends JsValue
case class JsNumber(num: BigDecimal) extends JsValue
case object JNull extends JsValue
case class JsArray(arr: Array[JsValue]) extends JsValue
case class JsObject(obj: Map[String, JsValue]) extends JsValue
```

# Using Json

##

There are several ways of using JSON within a play framework project.

## Parsing

```scala
import play.api.libs.json._

val jsonString = """
{
  "key": [true, false, 1, 3, {"object": 3}]
}
"""

val jsValue: JsValue = Json.parse(jsonString)
```

## Traversing

```scala
val key: Option[JsValue] = jsValue \ "key"
val firstElem: Option[JsValue] = jsValue \ "key" \ 0
```

# Serializing

##

Use the `Reads` and `Writes` typeclasses to convert to and from scala objects

```scala
case class Car(brand: String, model: String)

implicit val writesCar: Writes[Car] = Json.writes[Car]
implicit val readsCar: Reads[Car] = Json.reads[Car]
```

# Json and HTTP

#

Json can be read directly in the HTTP

# Persistence

## Persistence

Connect to a SQLite database.

# Using the HTTP client

## Http client

```scala
import play.api.libs.ws._
import play.api.http.HttpEntity
import scala.concurrent.ExecutionContext

val ws: WSClient = 
```


