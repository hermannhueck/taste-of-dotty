package dotty.samples._03context

// use _ (or ? in future Scala 3.x) for wildcard type
trait Functor[F[_]]:
  extension [A](x: F[A])
    def map[B](f: A => B): F[B]


// trait Monad[F[_]] extends Functor[F] // use _ (or ? in future Scala 3.x) for wildcard type
trait Monad[F[_]] extends Functor[F]:

  // intrinsic abstract methods for Monad

  def pure[A](a: A): F[A]

  extension [A](fa: F[A])
    def flatMap[B](f: A => F[B]): F[B]

  // other concrete methods

  extension [A](fa: F[A])
    override def map[B](f: A => B): F[B] = flatMap(fa)(f andThen pure)

  extension [A](fa: F[F[A]])
    def flatten: F[A] = flatMap(fa)(identity)
end Monad

object Monad:

  given Monad[List] with
    override def pure[A](a: A): List[A] = List(a)
    extension [A](fa: List[A])
      override def flatMap[B](f: A => List[B]): List[B] =
        fa flatMap f

  given Monad[Option] with
    override def pure[A](a: A): Option[A] = Some(a)
    extension[A](fa: Option[A])
      override def flatMap[B](f: A => Option[B]): Option[B] =
        fa flatMap f
  
  // given [L]: Monad[[R] =>> Either[L, R]]
  given[L]: Monad[Either[L, *]] with // requires -Ykind-projector
    override def pure[A](a: A): Either[L, A] = Right(a)
    extension [A](fa: Either[L, A])
      override def flatMap[B](f: A => Either[L, B]): Either[L, B] =
        fa flatMap f

end Monad
