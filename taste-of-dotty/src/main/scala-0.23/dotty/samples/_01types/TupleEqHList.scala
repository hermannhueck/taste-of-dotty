package dotty.samples._01types

object TupleEqHList {

  // Scala 2 + 3: Tuple syntax
  val isb1: (Int, String, Boolean) = (42, "foo", true)
  // Scala 3: HList syntax
  val isb2: Int *: String *: Boolean *: Unit = 42 *: "foo" *: true *: ()
  // HList in Scala 2 with 'shapeless'
  // val isb3: Int :: String :: Boolean :: HNil = 42 :: "foo" :: true :: HNil

  summon[(Int, String, Boolean) =:= Int *: String *: Boolean *: Unit] // identical types

  @main def testTupleHList: Unit =

    import util._
    import scala.util.chaining._
    import scala.language.implicitConversions

    printStartLine()

    isb1 pipe println // (42,foo,true)
    isb2 pipe println // (42,foo,true)
    assert(isb1 == isb2) // identical types

    printEndLine()
}