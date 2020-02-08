package dotty.samples.context

object TypeclassDerivationCodeStructure {

  import scala.deriving._

  // type class 'Eq'
  trait Eq[T]
    def eqv(x: T, y: T): Boolean
    def (x: T) === (y: T): Boolean = eqv(x, y)
    def (x: T) !== (y: T): Boolean = !eqv(x, y)


  // type class 'Eq' companion
  object Eq 
    inline given derived[T](given m: Mirror.Of[T]): Eq[T] =
      // use Mirror for the implementation
      ???

  // type Tree with derived instance of Eq
  enum Tree[+T] derives Eq
    case Leaf(elem: T)
    case Node(left: Tree[T], right: Tree[T])

  import Tree._

  given Eq[Tree[Int]] = summon[Eq[Tree[Int]]]

  // check equality of two trees using the given instance of Eq[Tree[Int]]
  val tree1 = Node(Leaf(1), Node(Leaf(2), Leaf(3)))
  val tree2 = Node(Leaf(1), Node(Leaf(2), Leaf(3)))
  val tree3 = Node(Leaf(2), Leaf(3))

  assert(tree1 === tree2)
  assert(tree1 !== tree3)
}