package dotty.samples

object ExplicitNulls {
  // error: found `Null`,  but required `String`
  val s1: String = null

  // Ok
  val s2: String | Null = null


  // Unsoundness:

  class C {
   val f: String = foo(f)
   def foo(f2: String): String = if (f2 == null) "field is null" else f2
  }
  val c = new C()
  // c.f == "field is null"


  // Equality Checks:

  val x: String = ???
  val y: String | Null = ???

  def checks =

    x == null       // error: Values of types String and Null cannot be compared with == or !=
    x eq null       // error
    "hello" == null // error

    y == null       // ok
    y == x          // ok

    (x: String | Null) == null  // ok
    (x: Any) == null            // ok


  // Working with Null:
  // The extension method 'nn' casts nullability away.
  val strOrNull: String|Null = "foo"
  // val str: String = strOrNull.nn
}