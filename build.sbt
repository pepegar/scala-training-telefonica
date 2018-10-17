ThisBuild / scalaVersion := "2.12.4"

/******************************************************************
 *                          EXERCISES                             *
 ******************************************************************/

val playDeps = Seq(
  guice,
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
  "com.h2database" % "h2" % "1.4.192",

  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
)

lazy val exercise1 = project
  .in(file("exercises/exercise1"))

lazy val exercise2 = project
  .in(file("exercises/exercise2"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)


/******************************************************************
 *                          EXAMPLES                              *
 ******************************************************************/
lazy val simpleExample = project
  .in(file("examples/simple"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)

lazy val parsingJsonHttpExample = project
  .in(file("examples/parsingJsonHttp"))
  .settings(libraryDependencies ++= playDeps)
  .enablePlugins(PlayScala)


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
