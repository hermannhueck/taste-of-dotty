package dotty.samples._03context.joiner

// Joiner in fact is Monoid.

trait Joiner[A] {
  def zero: A
  def join(lhs: A, rhs: A): A
  def joinAll(as: A*): A =
    as.fold(zero)(join)
}

object Joiner {

  def apply[A: Joiner]: Joiner[A] = implicitly

  implicit val intJoiner: Joiner[Int] = new Joiner[Int] {
    override def zero: Int = 0
    override def join(lhs: Int, rhs: Int): Int =
      lhs + rhs
  }

  implicit def listJoiner[A]: Joiner[List[A]] = new Joiner[List[A]] {
    override def zero: List[A] = List.empty[A]
    override def join(lhs: List[A], rhs: List[A]): List[A] =
      lhs ++ rhs
  }

  final implicit class JoinSyntax[A](private val lhs: A) extends AnyVal {
    @inline def join (rhs: A)(implicit joiner: Joiner[A]): A =
      joiner.join(lhs, rhs)
  }

  final implicit class ListSyntax[A](private val as: List[A]) extends AnyVal {
    @inline def joinAll(implicit joiner: Joiner[A]): A =
      joiner.joinAll(as: _*)
  }
}