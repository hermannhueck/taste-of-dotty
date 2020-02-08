package dotty.samples

val l1 = List(1, 2, 3)
val l2 = List(10, 20, 30)

val tuples: List[(Int, Int)] = l1 zip l2

// Scala 2 style mapping function with pattern matching
val sums1 = tuples map { case (x, y) => x + y }

// Scala 3 style mapping function with untupled parameters
val sums2 = tuples map { (x, y) => x + y }
val sums3 = tuples map { _ + _ }


@main def ParameterUntupling: Unit =
  
  import scala.util.chaining._
  import scala.language.implicitConversions
  import util._

  printStartLine()

  println(s"sums1 = $sums1")
  println(s"sums2 = $sums2")
  println(s"sums3 = $sums3")
  
  printEndLine()