val projectName = "taste-of-dotty"
val projectVersion = "0.1.0"

// val dottyVersion = "0.20.0-RC1"
val dottyVersion = dottyLatestNightlyBuild.get
val scala2xVersion = "2.13.1"

inThisBuild(
  Seq(
    version := projectVersion,
    // To make the default compiler and REPL use Dotty
    scalaVersion := dottyVersion,
    // To cross compile with Dotty and Scala 2
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
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := projectName,
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "build",
    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % Test
    ) ++ {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) =>
          Seq(
            "org.typelevel" %% "cats-core" % "2.0.0",
            // https://github.com/typelevel/kind-projector
            compilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)
          )
        case _ =>
          Seq.empty
      }
    },
    scalacOptions ++= {
      val sv = scalaVersion.value
      println(s"\n>>>>>          compiling for Scala $sv\n")
      if (sv.startsWith("0."))
        Seq("-strict")
      else
        Seq.empty
    }
  )
