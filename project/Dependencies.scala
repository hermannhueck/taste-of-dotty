import sbt._
import dotty.tools.sbtplugin.DottyPlugin.autoImport._

object Dependencies {

  val catsEffectVersion = "2.0.0"
  val scalaTestVersion = "3.1.0"
  val scalaCheckVersion = "1.14.3"
  val junitVersion = "0.11"
  val kindProjectorVersion = "0.11.0"

  lazy val commonDependencies = 
    Seq(
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion % Test,
    )
  
  lazy val javaDependencies =
    Seq("com.novocode" % "junit-interface" % junitVersion % Test)
  
  def dependenciesFor(scalaVersion: String): Seq[ModuleID] =
    if (scalaVersion.startsWith("0.")) {
      // Dotty dependencies
      commonDependencies.map(_.withDottyCompat(scalaVersion)) ++
      javaDependencies
    } else {
      // Scala 2.x dependencies
      commonDependencies ++
      javaDependencies ++
      Seq( compilerPlugin("org.typelevel" % "kind-projector" % kindProjectorVersion cross CrossVersion.full) )
    }
}
