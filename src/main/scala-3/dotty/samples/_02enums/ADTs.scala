package dotty.samples._02enums

object ADTs {

  enum Option[+T]:
    case Some(x: T)
    case None

  // The extends clauses that were omitted in the example above can also be given explicitly:

  enum Option2[+T]:
    case Some(x: T) extends Option2[T]
    case None       extends Option2[Nothing]

  // As for normal enum values, the cases of an enum are all defined in the enums companion object.
  // So it's Option.Some and Option.None unless the definitions are "pulled out" with an import:

  val some = Option.Some("hello")
  val none = Option.None

  // Note that the type of the expressions above is always Option.
  // That is, the implementation case classes are not visible in the result types of their apply methods.
  // This is a subtle difference with respect to normal case classes. The classes making up the cases do exist,
  // and can be unveiled by constructing them directly with a new.

  val some2: Option.Some[Int] = new Option.Some(2)

  // As all other enums, ADTs can define methods. For instance, here is Option again,
  // with an isDefined method and an Option(...) constructor in its companion object.

  enum Option3[+T]:
    case Some(x: T)
    case None
    def isDefined: Boolean = this match
      case None => false
      case some => true

  object Option3:
    def apply[T >: Null](x: T): Option3[T] =
      if x == null then None else Some(x)
  
  // Enumerations and ADTs have been presented as two different concepts.
  // But since they share the same syntactic construct, they can be seen simply as two ends of a spectrum
  // and it is perfectly possible to construct hybrids. For instance, the code below gives an implementation of Color
  // either with three enum values or with a parameterized case that takes an RGB value.

  enum Color(val rgb: Int):
    case Red   extends Color(0xFF0000)
    case Green extends Color(0x00FF00)
    case Blue  extends Color(0x0000FF)
    case Mix(mix: Int) extends Color(mix)

  // From Martin's talk at ScalaDays 2019
  enum Tree[+T]:
    case True extends Tree[Boolean]
    case False extends Tree[Boolean]
    case Zero extends Tree[Int]
    case Succ(n: Tree[Int]) extends Tree[Int]
    case IsZero(n: Tree[Int]) extends Tree[Boolean]
    case If(cond: Tree[Boolean],
            thenp: Tree[T],
            elsep: Tree[T]) extends Tree[T]
}