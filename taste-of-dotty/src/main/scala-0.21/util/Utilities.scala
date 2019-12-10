package util

def line(width: Int = 50): String =
  "\u2500" * width

def (what: String) boxed(width: Int = 50): String =
  s"${line(width)}\n$what\n${line(width)}"

def (what: String) colored (escape: String): String =
  s"$escape$what${Console.RESET}"

def (what: String) red: String =
  what.colored(Console.RED)
def (what: String) green: String =
  what.colored(Console.GREEN)
def (what: String) blue: String =
  what.colored(Console.BLUE)
