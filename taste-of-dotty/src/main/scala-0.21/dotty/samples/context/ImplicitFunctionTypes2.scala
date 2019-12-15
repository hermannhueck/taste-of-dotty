package dotty.samples.context


// Example: Postconditions

object PostConditions {

  opaque type WrappedResult[T] = T

  def result[T](given r: WrappedResult[T]): T = r

  def [T](x: T) ensuring(condition: (given WrappedResult[T]) => Boolean): T = {
    assert(condition(given x))
    x
  }
}

import PostConditions.{ensuring, result}
import util._

@main def ensuringPostcondition: Unit =

  println(lineStart())

  val xs = List(1, 2, 3)
  val sum = xs.sum.ensuring(result == 6)
  println(s"$xs.sum = $sum")

  println(lineEnd())
