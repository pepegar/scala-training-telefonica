ThisBuild / scalaVersion := "2.12.4"

val playDeps = Seq(
  guice,
  "com.typesafe.play" %% "play-server" % "2.6.13",
  "com.typesafe.play" %% "play-json" % "2.6.6",
  "com.h2database" % "h2" % "1.4.192",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

val akkaDeps = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.17",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.17" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

val akkaHttpDeps = Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.5",
  "com.typesafe.akka" %% "akka-stream" % "2.5.17",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

val akkaStreamsDeps = Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.5",
  "com.typesafe.akka" %% "akka-stream" % "2.5.17",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)
/******************************************************************
 *                          EXERCISES                             *
 ******************************************************************/
lazy val exercise1 = project
  .in(file("exercises/exercise1"))

lazy val exercise2 = project
  .in(file("exercises/exercise2"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)

lazy val exercise3 = project
  .in(file("exercises/exercise3"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)

lazy val exercise4 = project
  .in(file("exercises/exercise4"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val exercise5 = project
  .in(file("exercises/exercise5"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val exercise6 = project
  .in(file("exercises/exercise6"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val exercise7 = project
  .in(file("exercises/exercise7"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val exercise8 = project
  .in(file("exercises/exercise8"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val exercise10 = project
  .in(file("exercises/exercise10"))
  .settings(libraryDependencies ++= akkaHttpDeps)

lazy val exercise11 = project
  .in(file("exercises/exercise11"))
  .settings(libraryDependencies ++= akkaHttpDeps)

lazy val exercise12 = project
  .in(file("exercises/exercise12"))
  .settings(libraryDependencies ++= akkaHttpDeps)

lazy val exercise13 = project
  .in(file("exercises/exercise13"))
  .settings(libraryDependencies ++= akkaStreamsDeps)

lazy val exercise14 = project
  .in(file("exercises/exercise14"))
  .settings(libraryDependencies ++= akkaStreamsDeps)

lazy val exercise15 = project
  .in(file("exercises/exercise15"))
  .settings(libraryDependencies ++= akkaStreamsDeps)
/******************************************************************
 *                          EXAMPLES                              *
 ******************************************************************/
lazy val simple = project
  .in(file("examples/simple"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)

lazy val parsingJsonHttp = project
  .in(file("examples/parsingJsonHttp"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)

lazy val wsClient = project
  .in(file("examples/wsClient"))
  .settings(libraryDependencies ++= playDeps)
  .settings(libraryDependencies ++= Seq(
    ws
  ))
  .enablePlugins(PlayScala)

lazy val dbAccess = project
  .in(file("examples/dbAccess"))
  .settings(libraryDependencies ++= playDeps)
  .settings(libraryDependencies ++= Seq(
    jdbc,
    evolutions,
    "com.h2database" % "h2" % "1.4.192",
  ))
  .enablePlugins(PlayScala)

lazy val playTesting = project
  .in(file("examples/playTesting"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)

lazy val akkaRouting = project
  .in(file("examples/akkaRouting"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val akkaConfig = project
  .in(file("examples/akkaConfig"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val akkaResizer = project
  .in(file("examples/akkaResizer"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val akkaBroadcast = project
  .in(file("examples/akkaBroadcast"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val akkaTesting = project
  .in(file("examples/akkaTesting"))
  .settings(libraryDependencies ++= akkaDeps)


lazy val akkaSupervision = project
  .in(file("examples/akkaSupervision"))
  .settings(libraryDependencies ++= akkaDeps)

lazy val akkaHttpRouting = project
  .in(file("examples/akkaHttpRouting"))
  .settings(libraryDependencies ++= akkaHttpDeps)

lazy val akkaHttpJson = project
  .in(file("examples/akkaHttpJson"))
  .settings(libraryDependencies ++= akkaHttpDeps)

lazy val akkaHttpTestkit = project
  .in(file("examples/akkaHttpTestkit"))
  .settings(libraryDependencies ++= akkaHttpDeps)

lazy val akkaStreams = project
  .in(file("examples/akkaStreams"))
  .settings(libraryDependencies ++= akkaStreamsDeps)


lazy val akkaGraphDsl = project
  .in(file("examples/akkaGraphDsl"))
  .settings(libraryDependencies ++= akkaStreamsDeps)

lazy val akkaStreamsErrorHandling = project
  .in(file("examples/akkaStreamsErrorHandling"))
  .settings(libraryDependencies ++= akkaStreamsDeps)

lazy val akkaStreamsCombinators = project
  .in(file("examples/akkaStreamsCombinators"))
  .settings(libraryDependencies ++= akkaStreamsDeps)

/******************************************************************
 *                            DOCS                                *
 ******************************************************************/
lazy val docs = project
  .in(file("docs"))
  .settings(
    Tut / scalacOptions ++= Seq(
      "-encoding", "UTF-8", // 2 args
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions"
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.5"),
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "tut-out")
  .enablePlugins(TutPlugin)
