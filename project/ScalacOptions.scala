import sbt._

object ScalacOptions {

  val defaultScalacOptions = Seq(
    "-encoding",
    "UTF-8",                     // source files are in UTF-8
    "-deprecation",              // warn about use of deprecated APIs
    "-unchecked",                // warn about unchecked type parameters
    "-feature",                  // warn about misused language features
    "-explaintypes",             // explain type errors in more detail
    "-opt-warnings",             // enable optimizer warnings
    "-opt:l:inline",             // enable inline optimizations ...
    "-opt-inline-from:<source>", // ... from source files
    "-Xsource:2.13",             // Treat compiler input as Scala source for scala-2.13
    "-Xcheckinit",               // wrap field accessors to throw an exception on uninitialized access
    // "-Xlint",                    // enable handy linter warnings
    // "-Xfatal-warnings",       // fail the compilation if there are any warnings
    // "-Yno-adapted-args",      // (2.12) Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ywarn-dead-code",        // Warn when dead code is identified.
    "-Ywarn-extra-implicit",   // Warn when more than one implicit parameter section is defined.
    "-Ywarn-numeric-widen",    // Warn when numerics are widened.
    "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
    // "-Ywarn-unused:imports",   // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",   // Warn if a local definition is unused.
    "-Ywarn-unused:params",   // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",  // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates", // Warn if a private member is unused.
    "-Ywarn-value-discard",   // Warn when non-Unit expression results are unused.
    "-Ybackend-parallelism",
    "4",                                         // Enable paralellisation — change to desired number!
    "-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
    "-Ycache-macro-class-loader:last-modified"   // and macro definitions. This can lead to performance improvements.
  )

  val defaultDotcOptions = Seq(
    "-encoding",
    "UTF-8",                     // source files are in UTF-8
    "-deprecation", // emit warning and location for usages of deprecated APIs
    "-strict", // use strict type rules, which means some formerly legal code does not typecheck anymore
    "-migration", // mit warning and location for migration issues from Scala 2
    "-new-syntax", // require `then` and `do` in control expressions
    "-indent", // allow significant indentation
    "-feature", // emit warning and location for usages of features that should be imported explicitly
    "-Ykind-projector", // allow `*` as wildcard to be compatible with kind projector
    "-Yexplicit-nulls", // make reference types non-nullable. Nullable types can be expressed with unions: e.g. String|Null.
    // "-Yno-kind-polymorphism", // disable kind polymorphism (see https://dotty.epfl.ch/docs/reference/kind-polymorphism.html). Potentially unsound.
  )

  def scalacOptionsFor(scalaVersion: String): Seq[String] = {
    println(s"\n>>>>>          compiling for Scala $scalaVersion\n")
    if (scalaVersion.startsWith("0."))
      defaultDotcOptions
    else
      defaultScalacOptions
  }
}
