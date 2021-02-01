package dotty.samples._07dropped

object DoWhile {

  // Dropped: Do-While

  // Use equivalent while loop instead

  // For instance, instead of

  var i = 10
  def f(int: Int): Int = ???
  
  /*
  do
    i += 1
  while (f(i) == 0)
  */

  // one writes in old syntax:

  /*
  while ({
    i += 1
    f(i) == 0
  }) ()
  */

  // Under the new syntax rules, this code can be written also without the awkward ({...}) bracketing like this:

  while {
    i += 1
    f(i) == 0
  } do ()
  
}