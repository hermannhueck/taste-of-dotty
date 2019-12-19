package dotty.samples

// Opaque types aliases provide type abstraction without any overhead.

object Logarithms {

  opaque type Logarithm = Double

  object Logarithm {

    // These are the ways to lift to the logarithm type
    def apply(d: Double): Logarithm = math.log(d)

    def safe(d: Double): Option[Logarithm] =
      if (d > 0.0) Some(math.log(d)) else None
  }

  // Extension methods define opaque types' public APIs
  /*
  given logarithmOps: (x: Logarithm) extended with {
    def (x: Logarithm) toDouble: Double = math.exp(x)
    def (x: Logarithm) + (y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))
    def (x: Logarithm) * (y: Logarithm): Logarithm = Logarithm(x + y)
  }
  */
  def (x: Logarithm) toDouble: Double = math.exp(x)
  def (x: Logarithm) + (y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))
  def (x: Logarithm) * (y: Logarithm): Logarithm = Logarithm(x + y)
}


// Outside its scope, Logarithm is treated as a new abstract type. So the following operations would be valid
// because they use functionality implemented in the Logarithm object.

import scala.util.chaining._
import scala.language.implicitConversions
import util._

import Logarithms._
import Predef.{any2stringadd => _, _}

@main def logarisms: Unit =

  lineStart() pipe println

  val l = Logarithm(1.0) tap println
  val l2 = Logarithm(2.0) tap println
  val l3 = l * l2 tap println
  val l4 = l + l2 tap println
  
  // But the following operations would lead to type errors:
  // val d: Double = l       // error: found: Logarithm, required: Double
  // val l2: Logarithm = 1.0 // error: found: Double, required: Logarithm
  // l * 2                   // error: found: Int(2), required: Logarithm
  // l / l2                  // error: `/` is not a member for Logarithm

  lineEnd() pipe println

// Aside: the any2stringadd => _ import suppression is necessary since otherwise the universal + operation
// in Predef would take precedence over the + extension method in logarithmOps.
// We plan to resolve this wart by eliminating any2stringadd.

