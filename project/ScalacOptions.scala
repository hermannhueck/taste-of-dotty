import sbt._

object ScalacOptions {

  val defaultScalacOptions = Seq(
    "-encoding",
    "UTF-8",                      // source files are in UTF-8
    "-deprecation",               // warn about use of deprecated APIs
    "-unchecked",                 // warn about unchecked type parameters
    "-feature",                   // warn about misused language features
    "-explaintypes",              // explain type errors in more detail
    "-opt-warnings",              // enable optimizer warnings
    "-opt:l:inline",              // enable inline optimizations ...
    "-opt-inline-from:<source>",  // ... from source files
    "-Xsource:2.13",              // Treat compiler input as Scala source for scala-2.13
    "-Xcheckinit",                // wrap field accessors to throw an exception on uninitialized access
    "-Xlint",                     // enable handy linter warnings
    "-Wconf:any:warning-verbose", // Configure reporting of compiler warnings; use `help` for details.
    "-Werror",                    // Fail the compilation if there are any warnings. // previously: -Xfatal-warnings
    "-Wdead-code",                // Warn when dead code is identified.
    "-Wextra-implicit",           // Warn when more than one implicit parameter section is defined.
    "-Wnumeric-widen",            // Warn when numerics are widened.
    "-Wself-implicit",            // Warn when an implicit resolves to an enclosing self-definition.
    "-Wunused:imports",           // Warn if an import selector is not referenced.
    "-Wunused:patvars",           // Warn if a variable bound in a pattern is unused.
    "-Wunused:privates",          // Warn if a private member is unused.
    "-Wunused:locals",            // Warn if a local definition is unused.
    "-Wunused:explicits",         // Warn if an explicit parameter is unused.
    "-Wunused:implicits",         // Warn if an implicit parameter is unused.
    "-Wunused:params",            // Enable -Wunused:explicits,implicits.
    "-Wunused:linted",            // -Xlint:unused.
    "-Wvalue-discard",            // Warn when non-Unit expression results are unused.
    "-Ybackend-parallelism",
    "4",                                         // Enable paralellisation — change to desired number!
    "-Ycache-plugin-class-loader:last-modified", // Enables caching of classloaders for compiler plugins
    "-Ycache-macro-class-loader:last-modified",  // and macro definitions. This can lead to performance improvements.
    "-Ymacro-annotations"                        // Enable support for macro annotations, formerly in macro paradise.
  )

  val defaultDotcOptions = Seq(
    "-encoding",
    "UTF-8",            // source files are in UTF-8
    "-deprecation",     // emit warning and location for usages of deprecated APIs
    "-explain",         // Explain errors in more detail.
    "-explain-types",   // Explain type errors in more detail.
    "-migration",       // with warning and location for migration issues from Scala 2
    "-new-syntax",      // require `then` and `do` in control expressions
    "-indent",          // allow significant indentation
    "-feature",         // emit warning and location for usages of features that should be imported explicitly
    "-print-lines",     // Show source code line numbers.
    "-Ykind-projector", // allow `*` as wildcard to be compatible with kind projector
    "-Yexplicit-nulls"  // make reference types non-nullable. Nullable types can be expressed with unions: e.g. String|Null.
  )

  def scalacOptionsFor(scalaVersion: String): Seq[String] = {
    println(s"\n>>>>>          compiling for Scala $scalaVersion\n")
    if (scalaVersion.startsWith("0."))
      defaultDotcOptions
    else
      defaultScalacOptions
  }
}
