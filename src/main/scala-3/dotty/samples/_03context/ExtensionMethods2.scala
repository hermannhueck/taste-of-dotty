package dotty.samples._03context

extension (ss: Seq[String])
  def longestStrings: Seq[String] =
      val maxLength = ss.map(_.length).max
      ss.filter(_.length == maxLength)
  def longestString: String = longestStrings.head

extension [T](xs: List[T])
  def second: T = xs.tail.head
  def third: T = xs.tail.second

extension [T](xs: List[T])(using Ordering[T])
  def largest(n: Int) = xs.sorted.takeRight(n)
  def smallest(n: Int): List[T] = xs.sorted.take(n)
   def smallestIndices(n: Int): List[Int] =
      import math.Ordered.orderingToOrdered // or
      // import math.Ordering.Implicits.infixOrderingOps
      val limit = smallest(n).max
      xs.zipWithIndex.collect { case (x, i) if x <= limit => i }


import scala.util.chaining._
import util._

@main def ExtensionMethods2: Unit =

  printLine()
  "hello" pipe println
  printLine()
