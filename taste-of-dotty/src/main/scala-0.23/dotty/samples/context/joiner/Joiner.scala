package dotty.samples.context.joiner

// Joiner in fact is Monoid.

trait Joiner[A]:
  def zero: A
  def join(lhs: A, rhs: A): A
  inline def joinAll(as: A*): A =
    as.fold(zero)(join)

object Joiner:

  def apply[A: Joiner]: Joiner[A] = summon

  given Joiner[Int]:
    override def zero: Int = 0
    override def join(lhs: Int, rhs: Int): Int =
      lhs + rhs

  given [A] as Joiner[List[A]]:
    override def zero: List[A] = List.empty[A]
    override def join(lhs: List[A], rhs: List[A]): List[A] =
      lhs ++ rhs

  @scala.annotation.infix
  inline def [A: Joiner](lhs: A) join (rhs: A): A =
    Joiner[A].join(lhs, rhs)

  inline def [A: Joiner](as: List[A]) joinAll: A =
    Joiner[A].joinAll(as: _*)
