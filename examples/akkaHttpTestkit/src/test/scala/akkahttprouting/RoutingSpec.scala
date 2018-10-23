package akkahttprouting

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class RoutingSpec extends WordSpec with Matchers with ScalatestRouteTest {

  "route" should {
    "answer correctly" in {
      Get("/hello") ~> routing.route ~> check {
        responseAs[String] shouldEqual "<h1>Say hello to akka-http</h1>"
      }
    }
  }

}
