implicit def str2Int(s: String): Int =
  Integer.valueOf(s)

val xs = List("foo", "bar", "baz")

xs("1")
// xs("one") // error: NumberFormatExeption
