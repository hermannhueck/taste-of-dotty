object A:
  class TC
  given tc: TC = ???
  def f(using TC) = ???

object B:
  import A.* // imports all members of A except the given instances
  import A.given // imports only the given instances of A

object C:
  import A.{given, *} // import givens and non-givens with a single import

object D:
  import A.TC
  import A.given TC // importing by type
