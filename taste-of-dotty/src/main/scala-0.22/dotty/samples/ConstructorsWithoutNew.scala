package dotty.samples

val sb =
  StringBuilder("The keyword 'new'")
    .append(" is ")
    .append("optional")
    .append("!")

val s =
  sb.toString

import util.boxed

@main def withoutNew =
  println(s.boxed())