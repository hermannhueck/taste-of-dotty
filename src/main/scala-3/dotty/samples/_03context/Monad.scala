package dotty.samples._03context

// use _ or ? for wildcard type
trait Functor[F[_]]:
  extension [A, B](x: F[A])
    def map (f: A => B): F[B]


// trait Monad[F[_]] extends Functor[F] // use _ or ? for wildcard type
trait Monad[F[_]] extends Functor[F]:

  // intrinsic abstract methods for Monad

  def pure[A](a: A): F[A]

  extension [A, B](fa: F[A])
    def flatMap (f: A => F[B]): F[B]

  // other concrete methods

  extension [A, B] (fa: F[A])
    override def map (f: A => B): F[B] = flatMap(fa)(f andThen pure)

  extension [A](fa: F[F[A]])
    def flatten: F[A] = flatMap(fa)(identity)
end Monad

object Monad:

  given Monad[List] with
    override def pure[A](a: A): List[A] = List(a)
    extension [A, B](fa: List[A])
      override def flatMap(f: A => List[B]): List[B] =
        fa flatMap f

  given Monad[Option] with
    override def pure[A](a: A): Option[A] = Some(a)
    extension[A, B](fa: Option[A])
      override def flatMap(f: A => Option[B]): Option[B] =
        fa flatMap f
  
  // given [L]: Monad[[R] =>> Either[L, R]]
  given[L]: Monad[Either[L, *]] with // requires -Ykind-projector
    override def pure[A](a: A): Either[L, A] = Right(a)
    extension [A, B](fa: Either[L, A])
      override def flatMap(f: A => Either[L, B]): Either[L, B] =
        fa flatMap f

end Monad
