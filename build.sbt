import ScalacOptions._
import Dependencies._

val projectName = "taste-of-dotty"
val projectDescription = "A collection of examples show-casing the features of Dotty"
val projectVersion = "0.1.0"

val scala2Version = "2.13.5"
val scala3Version = "3.0.0-RC3"

lazy val root = project
  .in(file("."))
  .settings(
    name := projectName,
    description := projectDescription,
    version := projectVersion,
    scalaVersion := scala3Version,
    crossScalaVersions := Seq(scala3Version, scala2Version),
    scalacOptions ++= scalacOptionsFor(scalaVersion.value),
    libraryDependencies ++= dependenciesFor(scalaVersion.value),
    publish / skip := true,
    initialCommands :=
      s"""|
          |import scala.util.chaining._
          |println
          |""".stripMargin
  )
