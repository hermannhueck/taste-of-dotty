package dotty.samples._05new

object TraitParameters {

  // Dotty allows traits to have parameters, just like classes have parameters.

  trait Greeting(val name: String):
    def msg = s"How are you, $name"

  class C extends Greeting("Bob"):
    println(msg)

  // Arguments to a trait are evaluated immediately before the trait is initialized.

  // One potential issue with trait parameters is how to prevent ambiguities.
  // For instance, you might try to extend Greeting twice, with different parameters.

  // class D extends C with Greeting("Bill") // error: parameter passed twice

  // Should this print "Bob" or "Bill"? In fact this program is illegal,
  // because it violates one of the following rules for trait parameters:

  // - If a class C extends a parameterized trait T, and its superclass does not, C must pass arguments to T.

  // - If a class C extends a parameterized trait T, and its superclass does as well, C may not pass arguments to T.

  // - Traits may never pass arguments to parent traits.

  // Here's a trait extending the parameterized trait Greeting.

  trait FormalGreeting extends Greeting:
    override def msg = s"How do you do, $name"

  // As is required, no arguments are passed to Greeting. However, this poses an issue when defining a class that extends FormalGreeting:

  // class E extends FormalGreeting // error: missing arguments for `Greeting`.

  // The correct way to write E is to extend both Greeting and FormalGreeting (in either order):

  class E extends Greeting("Bob") with FormalGreeting
}
