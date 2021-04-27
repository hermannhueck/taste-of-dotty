package dotty.samples._01types

// A polymorphic method:
def foo[A](xs: List[A]): List[A] = xs.reverse

// A polymorphic function value:
val bar: [A] => List[A] => List[A]
//       ^^^^^^^^^^^^^^^^^^^^^^^^^
//       a polymorphic function type
       = [A] => (xs: List[A]) => foo[A](xs)

// type inferred
val bar2 = [A] => (xs: List[A]) => foo(xs)

@main def PolymorphicFunctionTypes: Unit =

  import util.*
  import scala.util.chaining.*
  
  line().green tap println

  // invocation of polymorphic method
  foo[Int](List(1, 2, 3)) tap println
  foo(List(1, 2, 3)) tap println

  println

  // invocation of polymorphic function
  bar[Int](List(1, 2, 3)) tap println
  bar(List(1, 2, 3)) tap println
  bar2(List(1, 2, 3)) tap println

  line().green tap println
