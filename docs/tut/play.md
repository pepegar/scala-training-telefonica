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

In play, your controllers need to extend `BaseController`, `AbstractController`

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

Tying controllers and Actions together in the `simple` example.

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

## Exercise 2.1

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

## Question

How would you represent that in Scala?

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

## Exercise 2.2

- `git checkout exercise-2.2-description`
- create `Reads` and `Writes` instances for the `Car` case class in the
`exercise2/model.scala` file.

# Json and HTTP

##

One can use `Reads` ands `Writes` typeclasses on scala classes to read
the body of HTTP request or to send the body of HTTP responses,
respectively.

##

To access the body of a request you use the following:

```scala
def other() =  Action { implicit req =>
  Json.fromJson[Car](req.body.asJson.get).asEither match {
    case Left(err) => BadRequest(JsError.toJson(err))
    case Right(car) =>
      // Do stuff with the car
      Ok(Json.toJson(car))
  }
}
```

##

But, since we've seen that `Action`s are functions, we can compose
them together.  This means executing one, and then another.  In our
case we'll create an action that tries to parse the body of the
request and returns it, and then another action that uses that body.

##

Parsing the body

```scala
def validateAs[A : Reads] = parse.json.validate(
  _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
)
```

##

Using the body

```scala
def index() = Action(validateAs[Car]) { implicit req =>
  val car : Car = req.body
  Ok(Json.toJson(car))
}
```

# Exercise 3

- checkout the `exercise3-description` tag.
- Create a CRUD application that stores users. A user is composed by
  an ID and a name.


# Persistence

#

##

Play has adapters for a wide number of SQL systems:
- MySQL
- Postgres
- H2
- SQLite

## Dependency

To add the dependency to build.sbt you can just do the following:

```
libraryDependencies += jdbc
```

And then the specific JDBC driver for your DB vendor:

```
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
```

## Configuration

DB configuration in play is controlled in the `application.conf` file.
An example configuration could be:

```
db {
  default {
    driver=org.h2.Driver
    url="jdbc:h2:mem:play"
  }
}
```

## Evolutions

Evolutions are the method play provides to evolve the model of your
application in a sane way.

## Evolutions

Evolutions are `.sql` files stored in the `/conf/evolutions/{dbname}`
folder, and they must have the following shape:

```sql
# Users schema

# --- !Ups
CREATE TABLE user (id bigint(20) NOT NULL AUTO_INCREMENT, fullname varchar(255) NOT NULL, PRIMARY KEY (id));

# --- !Downs
DROP TABLE user;
```

## Evolutions

As you can see, in the `.sql` file we have two statements, a `create`
table and a `drop` table.  That's because we should be able to apply
and unapply evolutions.

## Using the DB in your code

Since play follows the JSR-330 standard for dependency injection, we
can `@Inject` our `Database` object wherever we like.  Then, given the
`Database`, we can create queries and statements from it.

## Testing with databases

When testing our application we can programatically create evolutions
that put the DB in the state we need.

## Testing with databases

```scala
Evolutions.withEvolutions(database, SimpleEvolutionsReader.forDefault(
  Evolution(
    1,
    "CREATE TABLE user (id bigint(20) NOT NULL AUTO_INCREMENT, fullname varchar(255) NOT NULL, PRIMARY KEY (id));",
    "DROP TABLE user;"
  )
)
```

## Testing with databases

That programatic evolution has basically the same effect as the one we
created in a `.sql` file.

## example

Using the Database connection.  See the `dbAccess` example.

# HTTP client

#

##

Play comes with a powerful HTTP client based on AsyncHttpClient.

## Creating requests

You create requests by adding the URL they request to:

```scala
val req: WSRequest = WSClient.url("google.com")
```

## Creating requests

After creating the request we can use its fluid api with all of its
`.with*` methods to modify the req and make it contain everyting we
need:

```scala
import play.api.libs.ws._
import play.api.http.HttpEntity
import scala.concurrent.ExecutionContext

val req = ws.url("asdf")
      .withMethod("POST")
      .withBody(Json.toJson("hello dolly"))
```

## Creating requests

- `withQueryStringParameters(parameters: (String, String)*)`
- `withCookies(cookie: WSCookie*)`
- `withAuth(username: String, password: String, scheme: WSAuthScheme)`
- `withFollowRedirects(follow: Boolean)`
- `withRequestTimeout(timeout: Duration)`
- `withRequestFilter(filter: WSRequestFilter)`
- `withVirtualHost(vh: String)`
- `withProxyServer(proxyServer: WSProxyServer)`
- `withBody[T: BodyWritable](body: T)`
- `withMethod(method: String)`

# Testing the application

#

## Scalatest + play

Scalatest is the current standard for testing scala code.  They've
created, together with play framework team, a new package called
`scalatestplus-play` that helps a lot for testing our play app.

## Adding the dependency

```
libraryDependencies += 
  "org.scalatestplus.play" %% "scalatestplus-play" % "x.x.x" % Test
```

## Creating your test class

To test your play application, make your tests extend `PlaySpec` trait
and `GuiceOneAppPerSuite`.

- `PlaySpec` removes boilerplate for testing your controllers.
- `GuiceOneAppPerSuite` makes sure that, for every test file in your
  application, it starts a new app.  This helps make your tests
  independent from one another.
  
## Example

open the `playTesting` project

## Exercise 3.2

- checkout the `exercise-3.2-description` tag
- test your CRUD exercise!
