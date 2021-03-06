package dotty.playground.ord

@main def givensWithoutSymbols(): Unit =
  
  // instances without symbols
  given Ord[Int] with
    def compare(x: Int, y: Int) =
      if x < y then -1
      else if x > y then 1
      else 0

  given listOrd[T](using Ord[T]): Ord[List[T]] with
    def compare(xs: List[T], ys: List[T]): Int = (xs, ys) match
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


  val xs = List(1, 2, 3)

  import scala.util.chaining.*
  import util.*

  line().green.tap(println)

  max(2, 3).tap(println) // max of two Ints
  max(2, 3)(using summon[Ord[Int]]).tap(println) // max of two Ints - passing the given explicitly

  max(xs, Nil).tap(println) // max of two Lists
  minimum(xs).tap(println) // minimum element of a List
  maximum(xs)(using descending).tap(println) // maximum element of a List (in desc order)

  line().green.tap(println)
