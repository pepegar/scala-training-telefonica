package playtesting

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.Result
import play.api.test.FakeRequest

import scala.concurrent.Future
import scala.util.Success


class ControllerSpec extends PlaySpec with GuiceOneAppPerSuite {

  implicit val system = ActorSystem("test")
  implicit val ec = system.dispatcher
  implicit val mat = ActorMaterializer()

  "Controller" should {
    "return 200 OK when index route executed" in {

      val controller: Controller = fakeApplication().injector.instanceOf[Controller]

      controller.index().apply(FakeRequest())

      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: Future[String] = result.flatMap(_.body.consumeData).map(_.utf8String)
      bodyText map { t =>
        t mustBe "OK"
      }
    }
  }

}
