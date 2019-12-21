package migrate2dotty

object Geometry {

  opaque type Length = Double
  opaque type Area = Double

  enum Shape {

    case Circle(radius: Length)
    case Rectangle(width: Length, height: Length)

    def area: Area = this match
      case Circle(r) => math.Pi * r * r
      case Rectangle(w, h) => w * h

    def circumference: Length = this match
      case Circle(r) => 2 * math.Pi * r
      case Rectangle(w, h) => 2 * w + 2 * h
  }

  object Length
    def apply(d: Double): Length = d

  object Area
    def apply(d: Double): Area = d

  given (length: Length) extended with
    def double: Double = length

  given (area: Area) extended with
    def double: Double = area
}

import scala.util.chaining._
import scala.language.implicitConversions
import util._

import Geometry._
import Geometry.Shape._

@main def Shapes: Unit = {

  lineStart() pipe println

  val circle = Circle(Length(1.0))

  // val cArea: Double = circle.area // error: found: Area, required: Double
  val cArea: Area = circle.area
  val cAreaDouble: Double = cArea.double
  f"circle area: $cAreaDouble%.3f" pipe println

  // val cCircumference: Double = circle.circumference // error: found: Length, required: Double
  val cCircumference: Length = circle.circumference
  val cCircumferenceDouble: Double = cCircumference.double
  f"circle circumference: $cCircumferenceDouble%.3f" pipe println

  lineEnd() pipe println
}
