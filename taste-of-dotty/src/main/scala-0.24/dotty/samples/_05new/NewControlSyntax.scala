package dotty.samples._05new

object NewControlSyntax {

  var x = 42
  def f(x: Int): Int = x * x
  val xs = List(1, 2, 3)
  val ys = List(10, 20, 30)

  if x < 0
    "negative"
  else if x == 0
    "zero"
  else
    "positive"

  if x < 0 then -x else x

  while x >= 0 do x = f(x)

  for x <- xs if x > 0
  yield x * x

  for
    x <- xs
    y <- ys
  do
    println(x + y)

    
  // The rules in detail are:

    // - The condition of an if-expression can be written without enclosing parentheses if it is followed by a then.
    // - The condition of a while-loop can be written without enclosing parentheses if it is followed by a do.
    // - The enumerators of a for-expression can be written without enclosing parentheses or braces
    //   if they are followed by a yield or do.
    // - A do in a for-expression expresses a for-loop.
    // - Newline characters are not statement separators in a condition of an if or a while.
    //   So the meaning of newlines is the same no matter whether parentheses are present or absent.
    // - Newline characters are potential statement separators in the enumerators of a for-expression.

}
