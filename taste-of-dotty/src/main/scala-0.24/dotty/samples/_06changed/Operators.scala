package dotty.samples._06changed

import scala.annotation.{alpha, infix}

object Operators {

  // The @alpha Annotation

  trait Vec[T]

  object VecOps {
    @alpha("append") def [T] (xs: Vec[T]) ++= (ys: Vec[T]): Vec[T] = ???
  }
      
  // Here, the ++= operation is implemented (in Byte code or native code) under the name append.
  // The implementation name affects the code that is generated, and is the name under which code
  // from other languages can call the method. For instance, ++= could be invoked from Java like this:

  // VecOps.append(vec1, vec2)

  // The @alpha annotation has no bearing on Scala usages.
  // Any application of that method in Scala has to use ++=, not append.

  // The @infix Annotation

  // An @infix annotation on a method definition allows using the method as an infix operation. Example:

  trait MultiSet[T] {

    @infix
    def union(other: MultiSet[T]): MultiSet[T] = ???
  
    @infix
    def difference(other: MultiSet[T]): MultiSet[T] = ???
  
    @alpha("intersection")
    def *(other: MultiSet[T]): MultiSet[T] = ???
  }
  
  val s1, s2: MultiSet[Int] = new MultiSet[Int] {}
  
  s1 union s2         // OK
  s1.union(s2)        // also OK
  
  s1.difference(s2)   // OK
  s1 `difference` s2  // OK
  s1 difference s2    // gives a deprecation warning
  
  s1 * s2             // OK
  s1.*(s2)            // also OK, but unusual
  
  // The @infix annotation can also be given to a type:

  @infix type or[X, Y]
  val x: String or Int = ???

  // @infix annotations can be given to method definitions. The first non-receiver parameter list
  // of an @infix method must define exactly one parameter. Examples:

  trait Operations[S, R, A, B] {
    @infix def op1(x: S): R                  // ok
    @infix def op1[T](x: T)(y: S): R         // ok
    @infix def op3[T](x: T, y: S): R         // error: two parameters
    
    @infix def (x: A) op4 (y: B): R          // ok
    @infix def (x: A) op5 (y1: B, y2: B): R  // error: two parameters
  }
  
  // Syntax Change

  // Infix operators can now appear at the start of lines in a multi-line expression. Examples:

  val str = "hello"
    ++ " world"
    ++ "!"

  def condition(x: Int, xs: List[Int]) =
      x > 0
    || xs.exists(_ > 0)
    || xs.isEmpty
}

