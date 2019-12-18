package dotty.samples

@main def happyBirthday(age: Int, name: String, others: String*): Unit =

  val congrats = s"Happy Birthday at age $age to $name" ++ {
    if others.isEmpty
      ""
    else
      " and " ++ others.mkString(", ")
    } ++ "."

  import util._

  println(congrats.green.boxed())
  