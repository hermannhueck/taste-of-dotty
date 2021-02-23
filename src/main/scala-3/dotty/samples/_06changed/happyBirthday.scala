package dotty.samples._06changed

@main def happyBirthday(age: Int, name: String, others: String*): Unit =

  val congrats = s"Happy Birthday at age $age to $name" ++ {
    if others.isEmpty then
      ""
    else
      " and " ++ others.mkString(", ")
    } ++ "."

  import util.*

  println(congrats.green.boxed())
  