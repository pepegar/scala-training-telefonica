package akkagraphdsl

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, ClosedShape}

object Main extends App{

  implicit val actorSystem = ActorSystem("akka")
  implicit val materializer = ActorMaterializer()

  val graph = RunnableGraph.fromGraph(GraphDSL.create() {
    implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      val in = Source(1 to 20)
      val out = Sink.foreach(println)
      val bcast = builder.add(Broadcast[Int](2))
      val merge = builder.add(Merge[Int](2))
      val f1 = Flow[Int].map(_ + 1)
      val f2 = Flow[Int].map(_ + 1)
      val f3 = Flow[Int].map(_ + 1)
      val f4 = Flow[Int].map(_ + 1)
      val f5 = Flow[Int].filter(x => x % 2 ==0)

      in ~> f1 ~> bcast ~> f2 ~> merge ~> f4  ~> f5 ~> out
                  bcast ~> f3 ~> merge

      ClosedShape
  })

  graph.run()
  actorSystem.terminate()
}