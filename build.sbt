import ScalacOptions._
import Dependencies._

val projectName = "taste-of-dotty"
val projectDescription = "A collection of examples show-casing the features of Dotty"
val projectVersion = "0.1.0"

// val dottyVersion = "0.22.0-RC1"
val dottyVersion = dottyLatestNightlyBuild.get
val scala2xVersion = "2.13.1"

inThisBuild(
  Seq(
    description := projectDescription,
    version := projectVersion,
    scalaVersion := dottyVersion,
    crossScalaVersions := Seq(dottyVersion, scala2xVersion),
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(`taste-of-dotty`)
  .settings(
    name := "root",
    publish / skip := true
  )

lazy val `taste-of-dotty` = project
  .in(file(projectName))
  .settings(
    name := projectName,
    scalacOptions ++= scalacOptionsFor(scalaVersion.value),
    libraryDependencies ++= dependenciesFor(scalaVersion.value),
  )
