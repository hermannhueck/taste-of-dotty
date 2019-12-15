package dotty.samples

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

  object Length {
    def apply(d: Double): Length = d
  }
  object Area {
    def apply(d: Double): Area = d
  }

  def (length: Length) l2Double: Double = length
  def (area: Area) a2Double: Double = area
}

import util._

import Geometry._
import Geometry.Shape._

@main def runShapes: Unit =

  println(lineStart())

  val circle = Circle(Length(1.0))

  // val cArea: Double = circle.area // error: found: Area, required: Double
  val cArea: Area = circle.area
  val cAreaDouble: Double = cArea.a2Double
  println(f"circle area: $cAreaDouble%.3f")

  // val cCircumference: Double = circle.circumference // error: found: Length, required: Double
  val cCircumference: Length = circle.circumference
  val cCircumferenceDouble: Double = cCircumference.l2Double
  println(f"circle circumference: $cCircumferenceDouble%.3f")

  println(lineEnd())