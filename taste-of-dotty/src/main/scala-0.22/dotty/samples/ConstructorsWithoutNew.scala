package dotty.samples

val sb =
  StringBuilder("The keyword 'new'")
    .append(" is ")
    .append("optional")
    .append("!")

val s =
  sb.toString

import scala.util.chaining._
import scala.language.implicitConversions
import util._

@main def withoutNew =
  println(lineStart())
  println(s.red)
  println(lineEnd())