package exercise12

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}

class RouteSpec  extends WordSpec with Model with Matchers with ScalatestRouteTest {

  "route" should {
    "put user correctly" in {
      val user = User(3, "pepe")
      Get("/") ~> routing.routes ~> check {
        responseAs[String] shouldEqual ""
      }
    }
  }

}
