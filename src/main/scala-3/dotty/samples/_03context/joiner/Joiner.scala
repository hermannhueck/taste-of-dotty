package dotty.samples._03context.joiner

// Joiner in fact is Monoid.

trait Joiner[A]:
  def zero: A
  extension (lhs: A)
    infix def join (rhs: A): A
  def joinAll(as: A*): A =
    as.fold(zero)(_ join _)

object Joiner:

  def apply[A: Joiner]: Joiner[A] = summon

  given Joiner[Int] with
    override def zero: Int = 0
    extension (lhs: Int)
      infix inline override def join(rhs: Int): Int =
        lhs + rhs

  given [A]: Joiner[List[A]] with
    override def zero: List[A] = List.empty[A]
    extension (lhs: List[A] )
      infix inline override def join(rhs: List[A] ): List[A]  =
        lhs ++ rhs

  extension [A: Joiner](as: List[A])
    inline def joinAll: A =
      Joiner[A].joinAll(as: _*)
