package samples._04meta

object Macros {

  // Macros: Quotes and Splices

  import scala.quoted._

  inline def assert(expr: => Boolean): Unit =
    ${ assertImpl('expr) }
  
  def assertImpl(expr: Expr[Boolean])(using Quotes) = '{
    if !$expr then
      throw new AssertionError(s"failed assertion: ${${ showExpr(expr) }}")
  }
  
  // def showExpr(expr: Expr[Boolean])(given Quotes): Expr[String] =
    // '{ "<some source code>" } // Better implementation later in this document

  def showExpr[T](expr: Expr[T])(using Quotes): Expr[String] =
    val code: String = expr.show
    Expr(code)

  // If e is an expression, then '{e} represents the typed abstract syntax tree representing e.
  // If T is a type, then '[T] represents the type structure representing T.
  // The precise definitions of "typed abstract syntax tree" or "type-structure" do not matter for now,
  // the terms are used only to give some intuition. Conversely, ${e} evaluates the expression e,
  // which must yield a typed abstract syntax tree or type structure, and embeds the result as an expression
  // (respectively, type) in the enclosing program.

  // Quotations can have spliced parts in them; in this case the embedded splices are evaluated and embedded
  // as part of the formation of the quotation.

  // Quotes and splices can also be applied directly to identifiers. An identifier $x starting with a $ that appears inside a quoted expression or type is treated as a splice ${x}. Analogously, an quoted identifier 'x that appears inside a splice is treated as a quote '{x}. See the Syntax section below for details.

  // Quotes and splices are duals of each other. For arbitrary expressions e and types T we have:

  // ${'{e}} = e
  // '{${e}} = e
  // ${'[T]} = T
  // '[${T}] = T

  // Types for Quotations

  // The type signatures of quotes and splices can be described using two fundamental types:

    // Expr[T]: abstract syntax trees representing expressions of type T
    // Type[T]: type structures representing type T.

  // Quoting takes expressions of type T to expressions of type Expr[T] and it takes types T to expressions of type Type[T]. Splicing takes expressions of type Expr[T] to expressions of type T and it takes expressions of type Type[T] to types T.

  // The two types can be defined in package scala.quoted as follows:

  // package scala.quoted

  // sealed abstract class Expr[+T]
  // sealed abstract class Type[T]

  // Both Expr and Type are abstract and sealed, so all constructors for these types are provided by the system.
  // One way to construct values of these types is by quoting, the other is by type-specific lifting operations
  // that will be discussed later on.

  // Lifting Expressions

  import scala.quoted._

  enum Exp {
    case Num(n: Int)
    case Plus(e1: Exp, e2: Exp)
    case Var(x: String)
    case Let(x: String, e: Exp, in: Exp)
  }

  import Exp._

  val exp = Plus(Plus(Num(2), Var("x")), Num(4))
  val letExp = Let("x", Num(3), exp)
  
  /*
  import scala.quoted.{given, _}

  def compile(e: Exp, env: Map[String, Expr[Int]]): Expr[Int] = e match {
    case Num(n) =>
      Expr(n)
    case Plus(e1, e2) =>
      '{ ${ compile(e1, env) } + ${ compile(e2, env) } }
    case Var(x) =>
      env(x)
    case Let(x, e, body) =>
      '{ val y = ${ compile(e, env) }; ${ compile(body, env + (x -> 'y)) } }
  }

  // Running compile(letExp, Map()) would yield the following Scala code:

  '{ val y = 3; (2 + y) + 4 }

  */
  
}