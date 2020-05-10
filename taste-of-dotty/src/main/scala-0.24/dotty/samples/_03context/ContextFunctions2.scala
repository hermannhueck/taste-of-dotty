package dotty.samples._03context


// Example: Postconditions

object PostConditions:

  opaque type WrappedResult[T] = T

  def result[T](using r: WrappedResult[T]): T = r

  def [T](x: T) ensuring(condition: WrappedResult[T] ?=> Boolean): T =
    assert(condition(using x))
    x

import PostConditions.{ensuring, result}
import util._

@main def ContextFunctions2: Unit =

  printStartLine()

  val xs = List(1, 2, 3)
  val sum = xs.sum.ensuring(result == 6)
  println(s"$xs.sum = $sum")

  printEndLine()
