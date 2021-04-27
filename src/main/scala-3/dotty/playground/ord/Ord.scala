package dotty.playground.ord

// a type class
trait Ord[T]:
  def compare(x: T, y: T): Int
  extension (x: T)
    def < (y: T) = compare(x, y) < 0
    def > (y: T) = compare(x, y) > 0
