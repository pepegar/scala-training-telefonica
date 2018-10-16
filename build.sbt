ThisBuild / scalaVersion := "2.12.4"

lazy val exercise1 = project
  .in(file("exercises/exercise1"))

lazy val exercise2 = project
  .in(file("exercises/exercise2"))
  .settings(libraryDependencies ++= Seq(
    guice,
    "com.typesafe.play" %% "play-slick" % "3.0.1",
    "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
    "com.h2database" % "h2" % "1.4.192",

    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  ))
  .enablePlugins(PlayScala)

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
