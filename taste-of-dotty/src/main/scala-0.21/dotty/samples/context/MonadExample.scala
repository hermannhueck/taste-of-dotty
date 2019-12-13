package dotty.samples.context

trait Monad[F[_]] {

  // intrinsic abstract methods for Monad

  def [A](a: A) pure: F[A]
  def [A, B](fa: F[A]) flatMap (f: A => F[B]): F[B]

  // other concrete methods

  final def [A, B] (fa: F[A]) map(f: A => B): F[B] =
    flatMap(fa)(a => pure(f(a)))

  final def [A](fa: F[F[A]]) flatten: F[A] =
    flatMap(fa)(identity)
}

object Monad {

  given Monad[List]
    override def [A](a: A) pure: List[A] =
      List(a)
    override def [A, B](list: List[A]) flatMap (f: A => List[B]): List[B] =
      list flatMap f

  given Monad[Option]
    override def [A](a: A) pure: Option[A] =
      Option(a)
    override def [A, B](option: Option[A]) flatMap (f: A => Option[B]): Option[B] =
      option flatMap f
  
  given [L]: Monad[[R] =>> Either[L, R]]
    def [A](a: A) pure: Either[L, A] =
      Right(a)
    def [A, B](fa: Either[L, A]) flatMap (f: A => Either[L, B]): Either[L, B] =
      fa flatMap f
} 
 

def compute[F[_]: Monad](fInt1: F[Int], fInt2: F[Int]): F[(Int, Int)] =
  for
    i1 <- fInt1
    i2 <- fInt2
  yield (i1, i2)



import scala.language.implicitConversions
import scala.util.chaining._
import util._

@main def MonadExample: Unit =

  lineStart() pipe println

  println("----- List:")

  val l1 = List(1, 2, 3)
  val l2 = List(10, 20, 30, 40)

  val lResult = compute(l1, l2)
  println(lResult)

  val l3: List[List[Int]] =
    l1.map(i => l2.map(_ + i))
  println(l3)
  val lResult3 = l3.flatten
  println(lResult3)

  println("----- Option:")

  val o1 = Option(1)
  val o2 = Option(10)

  val oResult = compute(o1, o2)
  println(oResult)

  println("----- Either:")

  val e1 = Right(13).withLeft[String]
  val e2 = Right(21).withLeft[String]

  val eResult = compute(e1, e2)
  println(eResult)

  lineEnd() pipe println
