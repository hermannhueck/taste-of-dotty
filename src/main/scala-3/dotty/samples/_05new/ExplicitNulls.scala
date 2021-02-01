package dotty.samples._05new

object ExplicitNulls {

  // error: found `Null`,  but required `String`
  // val s1: String = null

  // Ok
  val s2: String | Null = null


  // Unsoundness:

  class C:
   val f: String = foo(f)
   def foo(f2: String|Null): String =
    val res =
      if f2 == null then
        "field is null"
      else
        f2
    println(s"\n$res\n")
    res


  @main def checkUnsoundness: Unit =
    val c = new C()
    // c.f == "field is null"


  // Equality Checks:

  val x: String = "foo"
  val y: String | Null = "foo"

  def checks =

    // x == null       // error: Values of types String and Null cannot be compared with == or !=
    // x eq null       // error
    // "hello" == null // error

    y == null       // ok
    y == x          // ok

    (x: String | Null) == null  // ok
    (x: Any) == null            // ok


  // Working with Null:
  // The extension method 'nn' casts away nullability.
  val strOrNull: String|Null = "foo"
  val str: String = strOrNull.nn
}