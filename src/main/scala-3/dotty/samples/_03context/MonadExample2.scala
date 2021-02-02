package dotty.samples._03context

object MonadExample2:
  trait Functor[F[_]] {}
// extension [A](x: F[A])
//   def map[B](f: A => B): F[B]

// given Functor[List] with
//    extension [A](xs: List[A])
//       def map[B](f: A => B): List[B] =
//          xs.map(f) // List already has a `map` method

// def assertTransformation[F[_]: Functor, A, B](expected: F[B], original: F[A], mapping: A => B): Unit =
//    assert(expected == original.map(mapping))

// // trait Monad[F[_]] extends Functor[F] // use _ or ? for wildcard type
// trait Monad[F[_]] extends Functor[F]:

//   // intrinsic abstract methods for Monad

//   def pure[A](a: A): F[A]

//   extension [A](x: F[A])
//       /** The fundamental composition operation */
//       def flatMap[B](f: A => F[B]): F[B]

//       /** The `map` operation can now be defined in terms of `flatMap` */
//       override def map[B](f: A => B) =
//         x.flatMap(f.andThen(pure))

//   extension [A](fa: F[F[A]])
//     def flatten: F[A] =
//       flatMap(fa)(identity)

// object Monad:

//   given Monad[List] with
//     def pure[A](x: A): List[A] =
//       List(x)
//     extension [A](xs: List[A])
//         override def flatMap[B](f: A => List[B]): List[B] =
//           xs.flatMap(f) // rely on the existing `flatMap` method of `List`

//   given Monad[Option] with
//     def pure[A](x: A): Option[A] =
//       Option(x)
//     extension [A](xo: Option[A])
//         override def flatMap[B](f: A => Option[B]): Option[B] =
//           xo.flatMap(f) // rely on the existing `flatMap` method of `Option`

//   // given [L]: Monad[[R] =>> Either[L, R]]
//   given [L]: Monad[Either[L, *]] with // requires -Ykind-projector
//     override def pure[A](a: A): Either[L, A] =
//       Right(a)
//     extension [A, B](fa: Either[L, A])
//       override def flatMap (f: A => Either[L, B]): Either[L, B] =
//         fa flatMap f

// def compute[F[_]: Monad, A, B](fa: F[A], fb: F[B]): F[(A, B)] =
//   for
//     a <- fa
//     b <- fb
//   yield (a, b)

// import scala.util.chaining._
// import util._

// @main def MonadExample: Unit =

//   printStartLine()

//   println("----- List:")

//   val l1 = List(1, 2, 3)
//   val l2 = List(10, 20, 30)

//   val lResult = compute(l1, l2)
//   println(lResult)

//   val l3: List[List[Int]] =
//     l1.map(i => l2.map(_ + i))
//   println(l3)
//   val lResult3 = l3.flatten
//   println(lResult3)

//   println("----- Option:")

//   val o1 = Option(1)
//   val o2 = Option(10)

//   val oResult = compute(o1, o2)
//   println(oResult)

//   println("----- Either:")

//   val e1 = Right(1).withLeft[String]
//   val e2 = Right(10).withLeft[String]

//   val eResult = compute(e1, e2)
//   println(eResult)

//   printEndLine()
