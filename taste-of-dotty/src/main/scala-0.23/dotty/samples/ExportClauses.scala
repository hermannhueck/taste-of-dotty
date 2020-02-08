package dotty.samples

class A
  def a1 = 42
  def a2 = a1.toString

class B
  private val a = new A
  export a.{a2 => aString} // exports a.a2 aliased to aString

@main def runExports: Unit =

  import scala.util.chaining._
  import scala.language.implicitConversions
  import util._

  printStartLine()

  val b = new B

  // a.a1 and a.a2 are not directly accessible as a is private in B.
  // The export claus makes a.a2 (alaiased to aString) accessible as a member of b
  val bString = b.aString ensuring (_ == 42.toString)
  bString pipe println

  printEndLine()
