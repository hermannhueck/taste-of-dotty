package dotty.samples

object Geometry {

  final case class Length(double: Double) extends AnyVal
  final case class Area(double: Double) extends AnyVal

  sealed trait Shape extends Product with Serializable {

    import Shape._

    def area: Area = this match {
      case Circle(r) => Area(math.Pi * r.double * r.double)
      case Rectangle(w, h) => Area(w.double * h.double)
    }

    def circumference: Length = this match {
      case Circle(r) => Length(2 * math.Pi * r.double)
      case Rectangle(w, h) => Length(2 * w.double + 2 * h.double)
    }
  }

  object Shape {
    final case class Circle(radius: Length) extends Shape
    final case class Rectangle(width: Length, height: Length) extends Shape
  }
}

import scala.util.chaining._
import scala.language.implicitConversions
import util._

import Geometry._
import Geometry.Shape._

object Shapes extends App {

  printStartLine()

  val circle = Circle(Length(1.0))

  // val cArea: Double = circle.area // error: found: Area, required: Double
  val cArea: Area = circle.area
  val cAreaDouble: Double = cArea.double
  f"circle area: $cAreaDouble%.3f" pipe println

  // val cCircumference: Double = circle.circumference // error: found: Length, required: Double
  val cCircumference: Length = circle.circumference
  val cCircumferenceDouble: Double = cCircumference.double
  f"circle circumference: $cCircumferenceDouble%.3f" pipe println

  printEndLine()
}
