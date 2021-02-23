package dotty.samples._05new

val sb =
  StringBuilder("The keyword 'new'")
    .append(" is ")
    .append("optional")
    .append("!")

val s =
  sb.toString

import scala.util.chaining.*
import util.*

@main def CreatorApplications =
  line().green pipe println
  println(s.red)
  line().green pipe println
