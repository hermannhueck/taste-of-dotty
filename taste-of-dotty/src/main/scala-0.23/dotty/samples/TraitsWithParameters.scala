package dotty.samples

trait Greeting(val name: String)
  def msg = s"How are you, $name"

class C extends Greeting("Bob")
  println(msg)

// class D extends C with Greeting("Bill")
// [error]      trait Greeting is already implemented by superclass C
// [error]      its constructor cannot be called again

/*
  - If a class C extends a parameterized trait T, and its superclass does not, C must pass arguments to T.

  - If a class C extends a parameterized trait T, and its superclass does as well, C may not pass arguments to T.

  - Traits may never pass arguments to parent traits.
*/