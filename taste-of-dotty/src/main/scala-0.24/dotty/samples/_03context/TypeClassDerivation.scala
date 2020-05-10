package dotty.samples._03context

object TypeclassDerivation {

  import scala.deriving._
  import scala.compiletime.{erasedValue, summonFrom}
  
  inline def summon[T]: T = summonFrom {
    case t: T => t
  }
  
  inline def summonAll[T <: Tuple]: List[Eq[?]] =
    inline erasedValue[T] match
      case _: Unit => Nil
      case _: (t *: ts) => summon[Eq[t]] :: summonAll[ts]
  
  trait Eq[T]:
    def eqv(x: T, y: T): Boolean
    def (x: T) === (y: T): Boolean = eqv(x, y)
    def (x: T) !== (y: T): Boolean = !eqv(x, y)

  object Eq:

    given Eq[Int]:
      def eqv(x: Int, y: Int) = x == y
  
    def check(elem: Eq[?])(x: Any, y: Any): Boolean =
      elem.asInstanceOf[Eq[Any]].eqv(x, y)
  
    def iterator[T](p: T) =
      p.asInstanceOf[Product].productIterator
  
    def eqSum[T](s: Mirror.SumOf[T], elems: List[Eq[?]]): Eq[T] =
      new Eq[T]:
        def eqv(x: T, y: T): Boolean =
          val ordx = s.ordinal(x)
          (s.ordinal(y) == ordx) && check(elems(ordx))(x, y)
  
    def eqProduct[T](p: Mirror.ProductOf[T], elems: List[Eq[?]]): Eq[T] =
      new Eq[T]:
        def eqv(x: T, y: T): Boolean =
          iterator(x).zip(iterator(y)).zip(elems.iterator).forall {
            case ((x, y), elem) => check(elem)(x, y)
          }
  
    inline given derived[T](using m: Mirror.Of[T]) as Eq[T] =
      val elemInstances = summonAll[m.MirroredElemTypes]
      inline m match
        case s: Mirror.SumOf[T]     => eqSum(s, elemInstances)
        case p: Mirror.ProductOf[T] => eqProduct(p, elemInstances)
}

import TypeclassDerivation._

enum Opt[+T] derives Eq:
  case Sm(t: T)
  case Nn

enum Tree[+T] derives Eq:
  case Leaf(elem: T)
  case Node(left: Tree[T], right: Tree[T])

@main def TypeclassDerivationTestApp: Unit =

  import Opt._

  val eqoi = summon[Eq[Opt[Int]]]

  assert(eqoi.eqv(Sm(23), Sm(23)))
  assert(!eqoi.eqv(Sm(23), Sm(13)))
  assert(!eqoi.eqv(Sm(23), Nn))

  import Tree._

  given Eq[Tree[Int]] = summon[Eq[Tree[Int]]]

  // check equality of two trees using the given instance of Eq[Tree[Int]]
  val tree1 = Node(Leaf(1), Node(Leaf(2), Leaf(3)))
  val tree2 = Node(Leaf(1), Node(Leaf(2), Leaf(3)))
  val tree3 = Node(Leaf(2), Leaf(3))

  // !!! endless loop
  // assert(tree1 === tree2)
  // assert(tree1 !== tree3)
