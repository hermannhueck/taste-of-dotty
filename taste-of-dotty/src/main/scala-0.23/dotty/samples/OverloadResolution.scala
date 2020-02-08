package dotty.samples

object OverloadResolution {

  // Looking Beyond the First Argument List

  def f(x: Int)(y: String): Int = 0
  def f(x: Int)(y: Int): Int = 0
  
  f(3)("")     // ok, but ambiguous overload error in Scala 2
  
  def g(x: Int)(y: Int)(z: Int): Int = 0
  def g(x: Int)(y: Int)(z: String): Int = 0
  
  g(2)(3)(4)     // ok, but ambiguous overload error in Scala 2
  g(2)(3)("")    // ok, but ambiguous overload error in Scala 2


  // Parameter Types of Function Values

  def h(x: Int, h: Int => Int) = h(x)
  def h(x: String, h: String => String) = h(x)
  h(40, _ + 2)
  // h("a", _.toUpperCase) // error: Found: String | UncheckedNull, required: String
}