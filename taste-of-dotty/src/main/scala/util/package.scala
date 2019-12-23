package object util {

  def line(width: Int = 80): String =
    "\u2500" * width

  def lineStart(width: Int = 80, color: String = Console.GREEN): String =
    line(width) ++ color

  def lineEnd(width: Int = 80): String =
    Console.RESET ++ line(width)

  implicit class StringSyntax(private val what: String) extends AnyVal {

    def boxed(width: Int = 80): String =
      s"${line(width)}\n$what\n${line(width)}"

    def colored(escape: String): String =
      s"$escape$what${Console.RESET}"

    def red: String =
      what.colored(Console.RED)

    def green: String =
      what.colored(Console.GREEN)

    def blue: String =
      what.colored(Console.BLUE)
  }
}
