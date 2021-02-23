package dotty.samples._01types

object TupleEqHList {

  // Scala 2 + 3: Tuple syntax
  val isb1: (Int, String, Boolean) = (42, "foo", true)
  // Scala 3: HList syntax
  val isb2: Int *: String *: Boolean *: EmptyTuple = 42 *: "foo" *: true *: EmptyTuple
  // HList in Scala 2 with 'shapeless'
  // val isb3: Int :: String :: Boolean :: HNil = 42 :: "foo" :: true :: HNil

  summon[(Int, String, Boolean) =:= Int *: String *: Boolean *: EmptyTuple] // identical types

  @main def testTupleHList: Unit =

    import util.*
    import scala.util.chaining.*

    printStartLine()

    isb1 pipe println // (42,foo,true)
    isb2 pipe println // (42,foo,true)
    assert(isb1 == isb2) // identical types

    printEndLine()
}
