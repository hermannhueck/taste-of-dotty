trait Greeting(val name: String):
  def msg = s"How are you, $name"

class C extends Greeting("Bob"):
  println(msg)

import scala.language.adhocExtensions

// class D extends C with Greeting("Bill"): // COMPILE ERROR
// [error]      trait Greeting is already implemented by superclass C
// [error]      its constructor cannot be called again
