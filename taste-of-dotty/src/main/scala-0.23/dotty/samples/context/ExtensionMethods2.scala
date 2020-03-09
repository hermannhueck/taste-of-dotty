package dotty.samples.context

object ExtensionInstances:

  extension ops: // the name 'ops' is arbitrary and optional

    def (xs: Seq[String]).longestStrings: Seq[String] =
      val maxLength = xs.map(_.length).max
      xs.filter(_.length == maxLength)

    def (xs: Seq[String]).longestString: String =
      xs.longestStrings.head
      
    def [T](xs: List[T]).second: T =
      xs.tail.head

object CollectiveExtensions:

  extension stringOps on (ss: Seq[String]): // the name 'stringOps' is arbitrary and optional

    def longestStrings: Seq[String] =
      val maxLength = ss.map(_.length).max
      ss.filter(_.length == maxLength)

    def longestString: String =
      longestStrings.head


  extension listOps on [T](xs: List[T]): // the name 'listOps' is arbitrary and optional
    def second: T = xs.tail.head
    def third: T = xs.tail.second

  extension on [T](xs: List[T])(using Ordering[T]):
    def largest(n: Int) = xs.sorted.takeRight(n)


import scala.language.implicitConversions
import scala.util.chaining._
import util._

@main def ExtensionMethods2: Unit =

  printLine()
  "hello" pipe println
  printLine()
