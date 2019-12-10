package dotty.playground

import scala.util.chaining._
import scala.language.implicitConversions // prevents warning in line 12

val r = scala.util.Random

def randomInt(): Int =
  r.nextInt

def printBoxed(what: String): Unit =
  what pipe boxed pipe println

def boxed(what: String): String =
  val line = "\u2500" * 50
  s"$line\n${what.toString}\n$line"

@main def myMain() =
  randomInt().toString pipe printBoxed
