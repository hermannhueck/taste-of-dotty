package dotty.playground.ord

@main def givensWithSymbols(): Unit =
  
  // instances with symbols
  given intOrd: Ord[Int] with
    def compare(x: Int, y: Int) =
      if x < y then -1
      else if x > y then 1
      else 0

  given listOrd[T](using ord: Ord[T]): Ord[List[T]] with
    def compare(xs: List[T], ys: List[T]): Int = (xs, ys) match
      case (Nil, Nil) => 0
      case (Nil, _) => -1
      case (_, Nil) => +1
      case (x :: xs1, y :: ys1) =>
         val fst = ord.compare(x, y)
         if fst != 0 then fst else compare(xs1, ys1)

  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) < 0 then y else x

  def maximum[T](xs: List[T])(using ord: Ord[T]): T =
    xs.reduceLeft(max)

  def descending[T](using asc: Ord[T]): Ord[T] = new Ord[T] {
    def compare(x: T, y: T) = asc.compare(y, x)
  }

  def minimum[T](xs: List[T])(using ord: Ord[T]) =
    maximum(xs)(using descending)


  val xs = List(1, 2, 3)

  import scala.util.chaining.*
  import util.*

  line().green.tap(println)

  max(2, 3).tap(println) // max of two Ints
  max(2, 3)(using intOrd).tap(println) // max of two Ints - passing the given explicitly

  max(xs, Nil).tap(println) // max of two Lists
  minimum(xs).tap(println) // minimum element of a List
  maximum(xs)(using descending).tap(println) // maximum element of a List (in desc order)

  line().green.tap(println)
