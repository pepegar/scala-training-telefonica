package exercise3

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import exercise3.model.User
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.mvc.{Headers, Result}
import play.api.test.FakeRequest

import scala.concurrent.Future

/**
  * Test your controller here.
  */
class ControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  import play.api.test.Helpers._

  implicit val system = ActorSystem("test")
  implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()


  val controller: Controller = fakeApplication().injector.instanceOf[Controller]

  "listAll" should {
    "return an empty list when no elements exist" in {
      val result: Future[Result] = controller.listAll().apply(FakeRequest())
      val a: JsValue = contentAsJson(result)

      a mustBe JsArray.empty
    }
  }

  "create" should {
    "add a new item to the collection" in {
      val user = User(1, "pepe")
      val req = FakeRequest(POST, "/", Headers.create(), user)

      // Create the request
      controller.create().apply(req)

      // read the list
      val resp = controller.listAll().apply(FakeRequest())

      contentAsJson(resp) mustBe JsArray.apply(List(Json.toJson(user)))
    }
  }

  // You get the idea :)
}
