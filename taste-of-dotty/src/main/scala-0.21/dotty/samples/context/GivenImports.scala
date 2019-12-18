package dotty.samples.context

object A
  class TC
  given tc: TC
  def f(given TC) = ???

object B
  import A._ // imports all members of A except the given instances
  import A.given // imports only the given instances of A

object C
  import A.{given, _} // import givens and non-givens with a single import

object D
  import A.{given A.TC} // importing by type
