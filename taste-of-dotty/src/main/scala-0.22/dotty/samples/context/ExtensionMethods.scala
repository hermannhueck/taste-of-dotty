package dotty.samples.context

case class Circle(x: Double, y: Double, radius: Double)

def (c: Circle) circumference: Double = c.radius * math.Pi * 2

val circle = Circle(0, 0, 1)

val cf1 = circle.circumference
val cf2 = circumference(circle)

import scala.language.implicitConversions
import scala.util.chaining._
import util._

@main def testExtensions: Unit =
  line() pipe println
  cf1 pipe println
  cf2 pipe println
  (cf1 == cf2) pipe println
  assert(cf1 == cf2)
  line() pipe println
