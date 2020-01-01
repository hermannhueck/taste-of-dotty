package dotty.samples.context.joiner

import util._
import scala.util.chaining._
import scala.language.implicitConversions

import Joiner._

object JoinerDemo extends App {

  printStartLine()

  s"2 join 3 = ${2 join 3}" pipe println
  
  val li1 = List(1,2,3)
  val li2 = List(11,12,13)
  val liJoined = li1 join li2
  s"$li1 join $li2 = $liJoined" pipe println

  val allIntsJoined = liJoined.joinAll
  s"all ints joined: $allIntsJoined" pipe println
  
  printEndLine()
}