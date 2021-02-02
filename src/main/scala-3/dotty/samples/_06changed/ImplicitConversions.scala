package dotty.samples._06changed

import scala.language.implicitConversions

object ImplicitConversions {

  // Examples

  // The first example is taken from scala.Predef. Thanks to this implicit conversion, it is possible
  // to pass a scala.Int to a Java method that expects a java.lang.Integer

  implicit def int2Integer(x: Int): java.lang.Integer =
    x.asInstanceOf[java.lang.Integer]

  // The second example shows how to use Conversion to define an Ordering for an arbitrary type,
  // given existing Orderings for other types:

  implicit def ordT[T, S](implicit
      conv: Conversion[T, S],
      ordS: Ordering[S]
  ): Ordering[T] = {
    // `ordS` compares values of type `S`, but we can convert from `T` to `S`
    ??? // (x: T, y: T) => ordS.compare(x, y)
  }

  class A(val x: Int) // The type for which we want an `Ordering`

  // Convert `A` to a type for which an `Ordering` is available:
  implicit val AToInt: Conversion[A, Int] = _.x

  implicitly[Ordering[Int]] // Ok, exists in the standard library
  implicitly[Ordering[A]] // Ok, will use the implicit conversion from
  // `A` to `Int` and the `Ordering` for `Int`.

  // In Scala 2, implicit values of a function type would be considered as potential views.
  // In Scala 3, these implicit value need to have type Conversion:

  // Scala 2:
  def foo2(x: Int)(implicit conv: Int => String): String = x

  // Becomes with Scala 3:
  def foo3(x: Int)(implicit conv: Conversion[Int, String]): String = x

  // Call site is unchanged:
  foo2(4)(_.toString)
  foo3(4)(_.toString)

  // Scala 2:
  implicit val myConverter2: Int => String = _.toString

  // Becomes with Scala 3:
  implicit val myConverter3: Conversion[Int, String] = _.toString

  // Motivation for the changes

  // The introduction of Conversion in Scala 3 and the decision to restrict implicit values of this type
  // to be considered as potential views comes from the desire to remove surprising behavior from the language:

  implicit val m: Map[Int, String] = Map(1 -> "abc")

  val x: String = 1 // scalac: assigns "abc" to x
  // Dotty: type error

}
