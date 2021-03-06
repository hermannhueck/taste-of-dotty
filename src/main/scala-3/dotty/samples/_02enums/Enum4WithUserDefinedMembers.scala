package dotty.samples._02enums

import scala.Console.*

enum Color4(val escape: String):

  case Red extends Color4(RED)
  case Green extends Color4(GREEN)
  case Blue extends Color4(BLUE)

  def colored(text: String) = s"$escape$text${Console.RESET}"



import util.*
import Color4.*

@main def printGreen(strings: String*) =
  val text = strings mkString " "
  val greenText = Green.colored(text)
  println(greenText.boxed())
