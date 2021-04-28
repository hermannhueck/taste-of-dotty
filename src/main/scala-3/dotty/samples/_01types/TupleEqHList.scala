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

    println(s"isb1: $isb1") // (42,foo,true)
    println(s"isb2: $isb2") // (42,foo,true)
    assert(isb1 == isb2) // identical types
    println(s"isb1 == isb2: ${isb1 == isb2}") // true

    println
    println(s"isb1.size: ${isb1.size}") // 3
    println(s"isb1 ++ isb2: ${isb1 ++ isb2}") // (42,foo,true,42,foo,true)
    println(s"(isb1 ++ isb2).splitAt(2): ${(isb1 ++ isb2).splitAt(2)}") // ((42,foo),(true,42,foo,true))
    println(s"isb1.take(2): ${isb1.take(2)}") // (42,foo)
    println(s"isb1.take(1): ${isb1.take(1)}") // (42)
    println(s"isb1.take(0): ${isb1.take(0)}") // ()
    println(s"isb1.drop(1): ${isb1.drop(1)}") // (foo,true)
    println(s"isb1.zip(isb2): ${isb1.zip(isb2)}") // ((42,42),(foo,foo),(true,true))
    println(s"isb1.map([T] => (x: T) => Option[T](x)): ${isb1.map([T] => (x: T) => Option[T](x))}") // (Some(42),Some(foo),Some(true))
    println(s"isb1.map([T] => (x: T) => List[T](x, x)): ${isb1.map([T] => (x: T) => List[T](x, x))}") // (List(42, 42),List(foo, foo),List(true, true))

    // println

    // def mapper[T](x: T): T = x match
    //   case i: Int => -i
    //   case s: String => s.toUppercase
    //   case b: Boolean => !b
    //   case _ => throw new Exception("illegal type")
    // val mapped = isb1.map(mapper)
    // println(mapped)

    printEndLine()
}
