package akkatesting

import akka.actor._
import akka.testkit.{TestKit, TestProbe}
import examples.MyActor
import examples.MyActor.{Hello, World}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class MyActorTest extends TestKit(ActorSystem("my-actor-test")) with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll = {
    TestKit.shutdownActorSystem(system)
  }

  "MyActor" should {
    "handle Hello messages correctly" in {
      val sender = TestProbe()
      val myActor = system.actorOf(MyActor.props, "myactor")
      sender.send(myActor, Hello)
      val state = sender.expectMsgType[World.type]
      state shouldBe MyActor.World
    }
  }
}
