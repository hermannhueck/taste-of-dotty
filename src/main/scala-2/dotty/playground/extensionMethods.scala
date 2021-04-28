package dotty.playground

case class Circle(x: Double, y: Double, radius: Double)

object extensionMethos extends App {

  def circumference(c: Circle) = c.radius * math.Pi * 2

  implicit class CircleExtension(private val c: Circle) extends AnyVal {
    @inline def circumference: Double = c.radius * math.Pi * 2
  }

  val circle = Circle(0, 0, 1)

  val cf1 = circle.circumference
  val cf2 = circumference(circle)

  import util._

  println(line().green)
  assert(cf1 == cf2)
  println(cf1 == cf2)
  println(line().green)
}
