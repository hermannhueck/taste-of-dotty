import scala.util.chaining.*

val r = scala.util.Random

def randomInt(): Int =
  r.nextInt

def printBoxed(what: String): Unit =
  what pipe boxed tap println

def boxed(what: String): String =
  val line = "\u2500" * 50
  s"$line\n${what.toString}\n$line"

randomInt().toString tap printBoxed
