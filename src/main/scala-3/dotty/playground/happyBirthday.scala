package dotty.playground

@main def happyBirthday(age: Int, name: String, others: String*): Unit =

  val congrats = s"Happy Birthday at age $age to $name" ++ {
    if others.isEmpty then
      ""
    else
      " and " ++ others.mkString(" and ")
    } ++ "."

  import util.*
  println(line().green)

  println(congrats)

  println(line().green)
