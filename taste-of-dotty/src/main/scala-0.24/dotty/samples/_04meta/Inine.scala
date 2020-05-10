package samples._04meta

object Inline {

  // Inline Definitions

  // inline is a new soft modifier that guarantees that a definition will be inlined at the point of use. Example:

  object Config:
    inline val logging = false
  
  object Logger:
  
    private var indent = 0
  
    inline def log[T](msg: String, indentMargin: =>Int)(op: => T): T =
      if Config.logging
        println(s"${"  " * indent}start $msg")
        indent += indentMargin
        val result = op
        indent -= indentMargin
        println(s"${"  " * indent}$msg = $result")
        result
      else
        op
  
  // Recursive Inline Methods
  
  // Inline methods can be recursive. For instance, when called with a constant exponent n,
  // the following method for power will be implemented by straight inline code without any loop or recursion.

  inline def power(x: Double, n: Int): Double =
    if n == 0 then 1.0
    else if n == 1 then x
    else
      val y = power(x, n / 2)
      if n % 2 == 0 then y * y else y * y * x
  
    // power(expr, 10)
      // translates to
      //
      //    val x = expr
      //    val y1 = x * x   // ^2
      //    val y2 = y1 * y1 // ^4
      //    val y3 = y2 * x  // ^5
      //    y3 * y3          // ^10

  
  // Parameters of inline methods can have an inline modifier as well.
  // This means that actual arguments to these parameters must be constant expressions. For example:

  // inline def power(x: Double, inline n: Int): Double

  // @inline and @forceInline
  // ...

  // Specializing Inline (Whitebox)

  class A

  class B extends A:
    def meth() = true
  
  transparent inline def choose(b: Boolean): A =
    if b
     A()
    else
      B()
  
  val obj1 = choose(true)  // static type is A
  val obj2 = choose(false) // static type is B
  
  // obj1.meth() // compile-time error: `meth` is not defined on `A`
  obj2.meth()    // OK

  transparent inline def zero(): Int = 0

  val one: 1 = zero() + 1
  
  // Inline Conditionals

  var x = 0
  def increaseBy(delta: Int) = x += delta
  def decreaseBy(delta: Int) = x -= delta
  
  inline def update(delta: Int) =
    inline if delta >= 0 then increaseBy(delta)
    else decreaseBy(-delta)
  
  // update can only be called with a constant value, otherwise the code will not compile
  update(22)
  update(-22)

  // Inline Matches

  // A match expression in the body of an inline method definition may be prefixed by the inline modifier.
  // If there is enough static information to unambiguously take a branch, the expression is reduced to that branch
  // and the type of the result is taken. If not, a compile-time error is raised that reports
  // that the match cannot be reduced.

  transparent inline def g(x: Any): Any = inline x match
    case x: String => (x, x) // Tuple2[String, String](x, x)
    case x: Double => x
  
  val res1: Double = g(1.0d) // Has type 1.0d which is a subtype of Double
  val res2: (String, String) = g("test") // Has type (String, String)
  
  trait Nat
  case object Zero extends Nat
  case class Succ[N <: Nat](n: N) extends Nat
  
  transparent inline def toInt(n: Nat): Int = inline n match
    case Zero => 0
    case Succ(n1) => toInt(n1) + 1
  
  final val natTwo = toInt(Succ(Succ(Zero)))
  val intTwo: 2 = natTwo
  
  // The scala.compiletime Package
  
  // The scala.compiletime package contains helper definitions that provide support for compile time operations over values.

  // constValue, constValueOpt, and the S combinator

  // constvalue is a function that produces the constant value represented by a type.

  import scala.compiletime.{constValue, S}

  transparent inline def toIntC[N]: Int =
    inline constValue[N] match
      case 0 => 0
      case _: S[n1] => 1 + toIntC[n1]
  
  final val ctwo = toIntC[2]

  // constValueOpt is the same as constValue, however returning an Option[T] enabling us to handle situations
  // where a value is not present. Note that S is the type of the successor of some singleton type.
  // For example the type S[1] is the singleton type 2.

  // erasedValue

  // The erasedValue function pretends to return a value of its type argument T.
  // In fact, it would always raise a NotImplementedError exception when called.
  // But the function can in fact never be called, since it is declared erased,
  // so can only be used at compile-time during type checking.

  // Using erasedValue, we can then define defaultValue as follows:

  import scala.compiletime.erasedValue

  transparent inline def defaultValue[T] = inline erasedValue[T] match
    case _: Byte => Some(0: Byte)
    case _: Char => Some(0: Char)
    case _: Short => Some(0: Short)
    case _: Int => Some(0)
    case _: Long => Some(0L)
    case _: Float => Some(0.0f)
    case _: Double => Some(0.0d)
    case _: Boolean => Some(false)
    case _: Unit => Some(())
    case _ => None
  
  // then ...
  
  val dInt: Some[Int] = defaultValue[Int]
  val dDouble: Some[Double] = defaultValue[Double]
  val dBoolean: Some[Boolean] = defaultValue[Boolean]
  val dAny: None.type = defaultValue[Any]

  // As another example, consider the type-level version of toInt below: given a type representing a Peano number,
  // return the integer value corresponding to it. Consider the definitions of numbers as in the Inline Match section above.
  // Here is how toIntT can be defined:

  transparent inline def toIntT[N <: Nat]: Int =
    inline scala.compiletime.erasedValue[N] match
      case _: Zero.type => 0
      case _: Succ[n] => toIntT[n] + 1
  
  final val two = toIntT[Succ[Succ[Zero.type]]]

  // erasedValue is an erased method so it cannot be used and has no runtime behavior.
  // Since toInt performs static checks over the static type of N we can safely use it
  // to scrutinize its return type (S[S[Z]] in this case).

  // error

  // The error method is used to produce user-defined compile errors during inline expansion.
  // It has the following signature:
  // inline def error(inline msg: String): Nothing

  // If an inline expansion results in a call error(msgStr)
  // the compiler produces an error message containing the given msgStr.

  import scala.compiletime.{error, code}

  inline def fail() =
    error("failed for a reason")
  // fail() // error: failed for a reason

  // or:

  inline def fail(p1: => Any) =
    error(code"failed on: $p1")
  // fail(identity("foo")) // error: failed on: identity("foo")
  
  // Summoning Implicits Selectively: summonFrom

  import scala.collection.immutable._

  // Consider for instance the problem of creating a TreeSet[T] or a HashSet[T] depending on
  // whether T has an Ordering or not. We can create a set of implicit definitions like this:

  trait SetFor[T, S <: Set[T]]

  class LowPriority:
    implicit def hashSetFor[T]: SetFor[T, HashSet[T]] =
      new SetFor[T, HashSet[T]] {}

  object SetsFor extends LowPriority:
    implicit def treeSetFor[T: Ordering]: SetFor[T, TreeSet[T]] =
      new SetFor[T, TreeSet[T]] {}

  // Clearly, this is not pretty...

  // The new summonFrom construct makes implicit search available in a functional context.
  // To solve the problem of creating the right set, one would use it as follows:

  import scala.compiletime.summonFrom

  inline def setFor[T]: Set[T] = summonFrom {
    case given ord: Ordering[T] => new TreeSet[T]
    case _                      => new HashSet[T]
  }
  
  // A summonFrom call takes a pattern matching closure as argument.
  // All patterns in the closure are type ascriptions of the form identifier : Type.

  // Patterns are tried in sequence. The first case with a pattern x: T such that an implicit value of type T
  // can be summoned is chosen. If the pattern is prefixed with given,
  // the variable x is bound to the implicit value for the remainder of the case.
  // It can in turn be used as an implicit in the right hand side of the case.
  // It is an error if one of the tested patterns gives rise to an ambiguous implicit search.

  // summonFrom applications must be reduced at compile time.

  // Consequently, if we summon an Ordering[String] the code above will return a new instance of TreeSet[String].

  summon[Ordering[String]]

  println(setFor[String].getClass) // prints class scala.collection.immutable.TreeSet
  
  class A1
  implicit val a1: A1 = new A1
  implicit val a2: A1 = new A1
  
  inline def f: Any = summonFrom {
    case given _: A1 => ???  // error: ambiguous implicits
  }
    
}