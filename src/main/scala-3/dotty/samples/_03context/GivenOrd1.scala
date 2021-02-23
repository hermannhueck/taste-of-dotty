package dotty.samples._03context

object Ord1 {

  // typeclass
  trait Ord[T]:
    def compare(x: T, y: T): Int
    extension (x: T)
      def < (y: T) = compare(x, y) < 0
      def > (y: T) = compare(x, y) > 0

  // instance for Int (with symbol)
  given intOrd: Ord[Int] with
    def compare(x: Int, y: Int) =
      if x < y then -1 else if x > y then +1 else 0

  given listOrd[T](using ord: Ord[T]): Ord[List[T]] with
    def compare(xs: List[T], ys: List[T]): Int =
      (xs, ys) match
        case (Nil, Nil) => 0
        case (Nil, _) => -1
        case (_, Nil) => +1
        case (x :: xs1, y :: ys1) =>
          val fst = ord.compare(x, y)
          if fst != 0 then
            fst
          else
            compare(xs1, ys1)

  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  def maximum[T](xs: List[T])(using ord: Ord[T]): T =
    xs.reduceLeft(max)

  def descending[T](using asc: Ord[T]): Ord[T] = new Ord[T] {
    def compare(x: T, y: T) = asc.compare(y, x)
  }

  def minimum[T](xs: List[T])(using ord: Ord[T]) =
    maximum(xs)(using descending)
}

import Ord1.* // imports only symbol which are not given
import Ord1.given // imports the givens (also the terms)

import scala.util.chaining.*
import util.*

@main def GivenOrd1: Unit =

  printStartLine()
  
  max(2, 3)(using intOrd) pipe ( x => println(s"max(2, 3) = $x"))
  max(2, 3) pipe ( x => println(s"max(2, 3) = $x"))
  
  val xs = List(1, 2, 3)
  println(s"\nxs = $xs\n")
  
  max(xs, Nil) pipe (x => println(s"max(xs, Nil) = $x"))
  minimum(xs) pipe (x => println(s"minimum(xs) = $x"))
  maximum(xs)(using descending) pipe (x => println(s"maximum(xs)(given descending) = $x"))
  maximum(xs)(using descending(using summon[Ord[Int]]))
  // maximum(xs)(given descending(given ListOrd(given IntOrd)))
      
  printEndLine()

end GivenOrd1
