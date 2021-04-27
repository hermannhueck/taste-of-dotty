package dotty.playground

@main def happyBirthday(age: Int, name: String, others: String*): Unit =

  val congrats = s"Happy Birthday at age $age to $name" ++ {
    if others.isEmpty then
      ""
    else
      " and " ++ others.mkString(", ")
    } ++ "."

  println(congrats)
