package dotty.samples._07dropped

object AutoApplication {

  // Dropped: Auto-Application

  // Previously an empty argument list () was implicitly inserted when calling a nullary method without arguments. E.g.

  def next[T](): T = ???
  // next     // previously expanded to next()

  // In Dotty, this idiom is an error.

  // In Dotty, the application syntax has to follow exactly the parameter syntax.
  // Excluded from this rule are methods that are defined in Java or that override methods defined in Java.
  // The reason for being more lenient with such methods is that otherwise everyone would have to write

  // xs.toString().length()

  // instead of

  // xs.toString.length

  // The latter is idiomatic Scala because it conforms to the uniform access principle. This principle states
  // that one should be able to change an object member from a field to a non-side-effecting method and back
  // without affecting clients that access the member. Consequently, Scala encourages to define such "property" methods
  // without a () parameter list whereas side-effecting methods should be defined with it. Methods defined in Java
  // cannot make this distinction; for them a () is always mandatory. So Scala fixes the problem on the client side,
  // by allowing the parameterless references. But where Scala allows that freedom for all method references,
  // Dotty restricts it to references of external methods that are not defined themselves in Dotty.
}