package dotty.samples._03context.equality

import scala.language.strictEquality

object MultiversialEquality {

  val x: Int = 42
  val s: String = "foo"
  
  assert(x == 42)
  assert(s == "foo")
  // assert(x != s) // does not compile with -language:strictEquality

  given CanEqual[Int, String] = CanEqual.derived
  given CanEqual[String, Int] = CanEqual.derived
  assert(x != s)
  assert(s != x)


  import scala.util.chaining._
    import util._

  @main def EqualityCheck: Unit =

    line().green pipe println
    "EqualityCheck" pipe println
    line().green pipe println
}