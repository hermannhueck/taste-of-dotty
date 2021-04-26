import sbt._

object Dependencies {

  val catsEffectVersion = "3.1.0"
  val scalaTestVersion = "3.2.8"
  val scalaCheckVersion = "1.15.3"
  val junitVersion = "0.11"
  val kindProjectorVersion = "0.11.3"

  lazy val commonDependencies =
    Seq(
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
      "org.scalacheck" %% "scalacheck" % scalaCheckVersion % Test
    )

  lazy val javaDependencies =
    Seq("com.novocode" % "junit-interface" % junitVersion % Test)

  def dottyDependencies(scalaVersion: String): Seq[ModuleID] =
    Seq.empty
  // Seq("ch.epfl.lamp" %% "dotty-staging" % scalaVersion)

  def dependenciesFor(scalaVersion: String): Seq[ModuleID] =
    if (scalaVersion.startsWith("3.")) {
      // Dotty dependencies
      commonDependencies.map(_.cross(CrossVersion.for3Use2_13)) ++
        dottyDependencies(scalaVersion) ++
        javaDependencies
    } else {
      // Scala 2.x dependencies
      commonDependencies ++
        javaDependencies ++
        Seq(
          compilerPlugin(
            "org.typelevel" % "kind-projector" % kindProjectorVersion cross CrossVersion.full
          )
        )
    }
}
