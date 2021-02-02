package dotty.samples._05new

object ParameterUntupling extends App {

  val xs: List[(Int, Int)] = List((1, 10), (2, 20), (3, 30))

  // Scala 2.x:
  val sums1 = xs map { case (x, y) => x + y } // List(11, 22, 33)

  // Scala 3.x:
  val sums2 = xs map { (x, y) => x + y } // List(11, 22, 33)
  val sums3 = xs.map(_ + _) // List(11, 22, 33)

  import scala.util.chaining._
  import util._

  line().green pipe println

  println(s"sums1 = $sums1")
  println(s"sums2 = $sums2")
  println(s"sums3 = $sums3")

  line().green pipe println
}
