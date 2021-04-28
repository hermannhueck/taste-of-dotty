package dotty.playground

val r = scala.util.Random

def randomInt(): Int =
  r.nextInt

def printBoxed(value: Any): Unit =
  println(boxed(value))

def boxed(value: Any): String =
  val line = "\u2500" * 50
  s"$line\n${value.toString}\n$line"

@main def topLevelDefsAndVals() =
  printBoxed(randomInt()) 
