package dotty.samples.context.equality

import scala.language.strictEquality

object MultiversialEquality {

  val x: Int = 42
  val s: String = "foo"
  
  assert(x == 42)
  assert(s == "foo")
  // assert(x != s) // does not compile with -language:strictEquality

  given Eql[Int, String] = Eql.derived
  given Eql[String, Int] = Eql.derived
  assert(x != s)
  assert(s != x)


  import scala.util.chaining._
  import scala.language.implicitConversions
  import util._

  @main def EqualityCheck: Unit =

    line().green pipe println
    "EqualityCheck" pipe println
    line().green pipe println
}