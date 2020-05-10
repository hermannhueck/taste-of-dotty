import ScalacOptions._
import Dependencies._

val projectName = "taste-of-dotty"
val projectDescription = "A collection of examples show-casing the features of Dotty"
val projectVersion = "0.1.0"

val dottyVersion = "0.24.0-RC1"
// val dottyVersion = dottyLatestNightlyBuild.get
val scala2xVersion = "2.13.2"

inThisBuild(
  Seq(
    description := projectDescription,
    version := projectVersion,
    scalaVersion := dottyVersion,
    crossScalaVersions := Seq(dottyVersion, scala2xVersion),
    publish / skip := true,
    initialCommands :=
      s"""|
          |import scala.util.chaining._
          |println
          |""".stripMargin // initialize REPL
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(`taste-of-dotty`)
  .settings(
    name := "root",
    sourceDirectories := Seq.empty
  )

lazy val `taste-of-dotty` = project
  .in(file(projectName))
  .settings(
    name := projectName,
    scalacOptions ++= scalacOptionsFor(scalaVersion.value),
    libraryDependencies ++= dependenciesFor(scalaVersion.value),
  )
