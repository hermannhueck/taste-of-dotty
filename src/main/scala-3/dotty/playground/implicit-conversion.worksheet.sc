// implicit def str2Int(s: String): Int =
//   Integer.valueOf(s).nn

given Conversion[String, Int] with
  def apply(s: String): Int =
    Integer.valueOf(s).nn

val xs = List("foo", "bar", "baz")

xs("1")
// xs("one") // error: NumberFormatExeption
