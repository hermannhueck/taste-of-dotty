package dotty.samples._06changed

object MatchExpressions {

  // The syntactical precedence of match expressions has been changed. match is still a keyword,
  // but it is used like an alphabetical operator. This has several consequences:

  // 1. match expressions can be chained:

  val xs = List(1, 2, 3)

  xs match
    case Nil => "empty"
    case x :: xs1 => "nonempty"
  match
    case "empty" => 0
    case "nonempty" => 1

  // 2. match may follow a period:

  val xsDefined = !xs.isEmpty
  if xsDefined
    && xs.match
         case Nil => false
         case _ => true
  then
    "nonempty"
  else
    "empty"
 
  // 3. The scrutinee of a match expression must be an InfixExpr. Previously the scrutinee could be followed
  // by a type ascription : T, but this is no longer supported. So x : T match { ... } now has to be written
  // (x: T) match { ... }.
}

