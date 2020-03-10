package dotty.samples._06changed

object OverloadResolution {

  // Overload resolution in Dotty improves on Scala 2 in two ways.
  // First, it takes all argument lists into account instead of just the first argument list.
  // Second, it can infer parameter types of function values even if they are in the first argument list.

  // Looking Beyond the First Argument List

  def f(x: Int)(y: String): Int = 0
  def f(x: Int)(y: Int): Int = 0
  
  f(3)("")     // ok

  def g(x: Int)(y: Int)(z: Int): Int = 0
  def g(x: Int)(y: Int)(z: String): Int = 0
  
  g(2)(3)(4)     // ok
  g(2)(3)("")    // ok

  // Parameter Types of Function Values

  // The handling of function values with missing parameter types has been improved.
  // We can now pass such values in the first argument list of an overloaded application,
  // provided that the remaining parameters suffice for picking a variant of the overloaded function.
  // For example, the following code compiles in Dotty, while it results in an missing parameter type error in Scala2:

  def h(x: Int, f: Int => Int) = f(x)
  def h(x: String, f: String => String) = f(x)
  // f("a", _.length)
  // f("a", x => x * x)
  h(40, _ + 2)
  // h("a", _.toUpperCase) // error: Found: String | UncheckedNull, required: String
}

