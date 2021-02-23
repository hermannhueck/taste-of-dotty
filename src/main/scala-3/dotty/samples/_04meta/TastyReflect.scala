package samples._04meta

object TastyReflect {

  // API: From quotes and splices to TASTy reflect trees and back

  // To provide reflection capabilities in macros we need to add
  // an implicit parameter of type scala.quoted.Quotes and import tasty.* from it in the scope where it is used.

  import scala.quoted.*

  inline def natConst(x: => Int): Int = ${natConstImpl('{x})}
  
  /*
  def natConstImpl(x: Expr[Int])(given qctx: Quotes): Expr[Int] = {
    import qctx.tasty.{_, given}
    ...
  }
  */

  // Sealing and Unsealing

  def natConstImpl(x: Expr[Int])(using quotes: Quotes): Expr[Int] =
    import quotes.reflect.*
    val xTree: Term = x.asTerm
    xTree match
      case Inlined(_, _, Literal(IntConstant(n: Int))) =>
        if n <= 0 then
          report.error("Parameter must be natural number")
          '{0}
        else
          xTree.asExprOf[Int]
      case _ =>
        report.error("Parameter must be a known constant")
        '{0}

  // Obtaining the underlying argument

  // inline def myMacro(param: => Boolean): Unit = ${ myMacroImpl('param) }

  // def myMacroImpl(param: Expr[Boolean])(using quotes: Quotes): Expr[Unit] =
  //   import qctx.tasty.{_, given}
  //   import util.*
  
  //   param.unseal.underlyingArgument match
  //     case t @ Apply(Select(lhs, op), rhs :: Nil) => ???

  // // call site example in different source file:
  // def checkCondition(): Boolean = 1 == 1
  // // myMacro(this.checkCondition())
  
  // Positions

  def macroImpl()(quotes: Quotes): Expr[Unit] =
    import quotes.reflect.*
    val pos = Position.ofMacroExpansion

    val path = pos.sourceFile.jpath.toString
    val start = pos.start
    val end = pos.end
    val startLine = pos.startLine
    val endLine = pos.endLine
    val startColumn = pos.startColumn
    val endColumn = pos.endColumn
    val sourceCode = pos.sourceCode
    // ...
    ???

  // Tree Utilities

  // scala.tasty.reflect.TreeUtils contains three facilities for tree traversal and transformations.

  // TreeAccumulator ties the knot of a traversal. By calling foldOver(x, tree)) we can dive in the tree node
  // and start accumulating values of type X (e.g., of type List[Symbol] if we want to collect symbols).
  // The code below, for example, collects the pattern variables of a tree.

  /*
  import scala.tasty.reflect.TreeUtils.*

  def collectPatternVariables(tree: Tree)(implicit ctx: Context): List[Symbol] = {
    val acc = new TreeAccumulator[List[Symbol]] {
      def apply(syms: List[Symbol], tree: Tree)(implicit ctx: Context): List[Symbol] = tree match {
        case Bind(_, body) => apply(tree.symbol :: syms, body)
        case _ => foldOver(syms, tree)
      }
    }
    acc(Nil, tree)
  }
  */
  
}
