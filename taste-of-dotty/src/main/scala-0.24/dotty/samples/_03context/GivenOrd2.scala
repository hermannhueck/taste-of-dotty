package dotty.samples._03context

object Ord2 {

  trait Ord[T]:
    def compare(x: T, y: T): Int
    def (x: T) < (y: T) = compare(x, y) < 0
    def (x: T) > (y: T) = compare(x, y) > 0

  // instance for Int (without symbol)
  given as Ord[Int]:
    def compare(x: Int, y: Int) =
      if x < y then -1 else if x > y then +1 else 0

  given [T] (using Ord[T]) as Ord[List[T]]:
    def compare(xs: List[T], ys: List[T]): Int =
      (xs, ys) match
        case (Nil, Nil) => 0
        case (Nil, _) => -1
        case (_, Nil) => +1
        case (x :: xs1, y :: ys1) =>
          val fst = summon[Ord[T]].compare(x, y)
          if fst != 0 then fst else compare(xs1, ys1)


  def max[T](x: T, y: T)(using Ord[T]): T =
    if summon[Ord[T]].compare(x, y) < 0 then y else x

  def maximum[T](xs: List[T])(using Ord[T]): T =
    xs.reduceLeft(max)

  def descending[T](using Ord[T]): Ord[T] = new Ord[T] {
    def compare(x: T, y: T) = summon[Ord[T]].compare(y, x)
  }

  def minimum[T](xs: List[T])(using Ord[T]) =
    maximum(xs)(using descending)
}

import Ord2._ // imports only symbol which are not given
// import Ord2.given // given types are found in implicit scope (typeclass companion object)

import scala.language.implicitConversions
import scala.util.chaining._
import util._

@main def GivenOrd2: Unit =

  printStartLine()
  
  // max(2, 3)(given intOrd) pipe ( x => println(s"max(2, 3) = $x"))
  max(2, 3) pipe ( x => println(s"max(2, 3) = $x"))
  
  val xs = List(1, 2, 3)
  println(s"\nxs = $xs\n")
  
  max(xs, Nil) pipe (x => println(s"max(xs, Nil) = $x"))
  minimum(xs) pipe (x => println(s"minimum(xs) = $x"))
  maximum(xs)(using descending) pipe (x => println(s"maximum(xs)(given descending) = $x"))
  maximum(xs)(using descending(using summon[Ord[Int]]))
  // maximum(xs)(given descending(given ListOrd(given IntOrd)))
      
  printEndLine()

end GivenOrd2