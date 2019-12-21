package migrate2dotty

def [T: Ordering](xs: List[T]) mimax: List[T] = {

  val sorted = xs.sorted.reverse

  val (middle, listEvenLength) =
    if sorted.length % 2 == 0
      (List.empty[T], sorted)
    else
      (List(sorted.head), sorted.tail)

  val (l1, l2) = listEvenLength
    .grouped(2) // Iterator[List[T]] each List with 2 elems
    .toList     // List[List[T]] each List with 2 elems
    .map {
      case List(x, y) => (x, y)
      case _ => throw new java.lang.IllegalStateException
    } // List[(T, T)]
    .unzip // (List[T], List[T])
    .swap  // (List[T], List[T])

  l1.sorted ++ middle ++ l2
}

@main def MiddleMax(ints: Int*) = {

  import scala.util.chaining._
  import scala.language.implicitConversions
  import util._

  lineStart() pipe println
  
  val l1 = List(1, 2, 3, 4, 5, 6) tap println
  l1.mimax.ensuring {
    _ == List(1, 3, 5, 6, 4, 2)
  } pipe println
  println
  
  val l2 = List(1, 2, 3, 4, 5) tap println
  l2.mimax.ensuring {
    _ == List(1, 3, 5, 4, 2)
  } pipe println
  println
    
  val l3 = ints.toList tap println
  l3.mimax pipe println

  lineEnd() pipe println
}