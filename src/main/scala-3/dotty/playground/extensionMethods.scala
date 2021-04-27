package dotty.playground

case class Circle(x: Double, y: Double, radius: Double)

extension (c: Circle)
  def circumference: Double = c.radius * math.Pi * 2

val circle = Circle(0, 0, 1)

val cf1 = circle.circumference
val cf2 = circumference(circle)

import util.*

@main def extensionMethos(): Unit =
  println(line().green)
  assert(cf1 == cf2)
  println(cf1 == cf2)
  println(line().green)
