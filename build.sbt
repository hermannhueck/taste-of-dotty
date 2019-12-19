val projectName = "taste-of-dotty"
val projectVersion = "0.1.0"

// val dottyVersion = "0.21.0-RC1"
val dottyVersion = dottyLatestNightlyBuild.get
val scala2xVersion = "2.13.1"

def dependenciesFor(deps: Seq[ModuleID], scalaVersion: String): Seq[ModuleID] = {
  deps.map(_.withDottyCompat(scalaVersion)) ++ 
  Seq("com.novocode" % "junit-interface" % "0.11" % Test ) ++ {
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, _)) => // Scala 2.x
        Seq(
          "org.typelevel" %% "cats-core" % "2.1.0",
          // https://github.com/typelevel/kind-projector
          compilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
        )
      case _ => // Scala 0.x == Dotty
        Seq.empty
    }
  }
}

def scalacOptionsFor(scalacOptions: Seq[String], scalaVersion: String): Seq[String] = {
  println(s"\n>>>>>          compiling for Scala $scalaVersion\n")
  if (scalaVersion.startsWith("0."))
    scalacOptions ++
    Seq("-strict", "-Ykind-projector", "-Yexplicit-nulls", "-language:implicitConversions")
  else
    scalacOptions
}

inThisBuild(
  Seq(
    version := projectVersion,
    // To make the default compiler and REPL use Dotty
    scalaVersion := dottyVersion,
    // To cross compile with Dotty and Scala 2
    crossScalaVersions := Seq(dottyVersion, scala2xVersion),
    scalacOptions ++= Seq(
      "-deprecation"
    ),
    libraryDependencies ++= Seq(
      // "org.typelevel" %% "cats-core" % "2.1.0"
    )
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(`taste-of-dotty`, migrate2dotty)
  .settings(
    name := "root",
    publish / skip := true
  )

lazy val `taste-of-dotty` = project
  .in(file(projectName))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := projectName,
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "build",
    libraryDependencies := dependenciesFor(libraryDependencies.value, scalaVersion.value),
    scalacOptions := scalacOptionsFor(scalacOptions.value, scalaVersion.value)
  )

lazy val migrate2dotty = project
  .in(file("migrate2dotty"))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "migrate2dotty",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "build",
    libraryDependencies := dependenciesFor(libraryDependencies.value, scalaVersion.value),
    scalacOptions := scalacOptionsFor(scalacOptions.value, scalaVersion.value)
  )
