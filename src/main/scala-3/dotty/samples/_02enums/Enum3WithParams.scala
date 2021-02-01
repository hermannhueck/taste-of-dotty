package dotty.samples._02enums

enum Color3(val escape: String):

  case Red extends Color3(Console.RED)
  case Green extends Color3(Console.GREEN)
  case Blue extends Color3(Console.BLUE)

// Methods defined for enums

val red = Color3.Red
// val red: Color3 = Red

val ord = red.ordinal
// val ord: Int = 0

val blue = Color3.valueOf("Blue")
// val blue: Color3 = Blue

val values = Color3.values
// val values: Array[Color3] = Array(Red, Green, Blue)
