package dotty.samples._03context

import scala.util.chaining.*
import util.*

def compute[F[_]: Monad, A, B](fa: F[A], fb: F[B]): F[(A, B)] =
  for
    a <- fa
    b <- fb
  yield (a, b)

@main def MonadExample: Unit =

  line().green pipe println

  s"${line(10)} List:".green pipe println

  val l1 = List(1, 2, 3)
  val l2 = List(10, 20, 30)

  val lResult = compute(l1, l2)
  println(lResult)

  val l3: List[List[Int]] =
    l1.map(i => l2.map(_ + i))
  println(l3)
  val lResult3 = l3.flatten
  println(lResult3)

  s"${line(10)} Option:".green pipe println

  val o1 = Option(1)
  val o2 = Option(10)

  val oResult = compute(o1, o2)
  println(oResult)

  s"${line(10)} Either:".green pipe println

  val e1 = Right(1).withLeft[String]
  val e2 = Right(10).withLeft[String]

  val eResult = compute(e1, e2)
  println(eResult)

  line().green pipe println
end MonadExample
