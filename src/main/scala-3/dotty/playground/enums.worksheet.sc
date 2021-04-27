enum Color:
  case Red, Green, Blue

// enum Color2 extends java.lang.Enum[Color2]:
//   case Red, Green, Blue

enum Color3(val escape: String):
  case Red extends Color3(Console.RED)
  case Green extends Color3(Console.GREEN)
  case Blue extends Color3(Console.BLUE)

val red = Color.Red
red.ordinal
Color.valueOf("Blue")
// Color.valueOf("Blues") // java.lang.IllegalArgumentException: enum case not found: Blues
Color.values
Color.values foreach println

enum Color4(val escape: String):
  case Red extends Color4(Console.RED)
  case Green extends Color4(Console.GREEN)
  case Blue extends Color4(Console.BLUE)
  // user defined method
  def colored(text: String) = s"$escape$text${Console.RESET}"

import Color4.*

val greenHello = Green.colored("Hello World!")
println(greenHello)
