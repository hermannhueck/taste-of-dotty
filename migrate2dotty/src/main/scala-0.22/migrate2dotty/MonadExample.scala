package migrate2dotty

trait Functor[F[?]] // use _ or ? for wildcard type
  def [A, B](x: F[A]) map (f: A => B): F[B]


// trait Monad[F[_]] extends Functor[F] // use _ or ? for wildcard type
trait Monad[F[?]] extends Functor[F]

  // intrinsic abstract methods for Monad

  def pure[A](a: A): F[A]
  def [A, B](fa: F[A]) flatMap (f: A => F[B]): F[B]

  // other concrete methods

  override def [A, B] (fa: F[A]) map (f: A => B): F[B] =
    flatMap(fa)(f andThen pure)

  def [A](fa: F[F[A]]) flatten: F[A] =
    flatMap(fa)(identity)


object Monad {

  given Monad[List]
    override def pure[A](a: A): List[A] = List(a)
    override def [A, B](list: List[A]) flatMap (f: A => List[B]): List[B] =
      list flatMap f

  given Monad[Option]
    override def pure[A](a: A): Option[A] = Some(a)
    override def [A, B](option: Option[A]) flatMap (f: A => Option[B]): Option[B] =
      option flatMap f
  
  given [L]: Monad[[R] =>> Either[L, R]]
    def pure[A](a: A): Either[L, A] = Right(a)
    def [A, B](fa: Either[L, A]) flatMap (f: A => Either[L, B]): Either[L, B] =
      fa flatMap f

  /*
  // TODO: placeholder * not yet implemented in current nightly at 2019-12-17
  given [L]: Monad[Either[L, *]]
    def pure[A](a: A): Either[L, A] = Right(a)
    def [A, B](fa: Either[L, A]) flatMap (f: A => Either[L, B]): Either[L, B] =
      fa flatMap f
  */
} 
 

def compute[F[?]: Monad, A, B](fa: F[A], fb: F[B]): F[(A, B)] =
  for
    a <- fa
    b <- fb
  yield (a, b)



import scala.language.implicitConversions
import scala.util.chaining._
import util._

@main def MonadExample: Unit =

  lineStart() pipe println

  println("----- List:")

  val l1 = List(1, 2, 3)
  val l2 = List(10, 20, 30)

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

  val e1 = Right(1).withLeft[String]
  val e2 = Right(10).withLeft[String]

  val eResult = compute(e1, e2)
  println(eResult)

  lineEnd() pipe println
