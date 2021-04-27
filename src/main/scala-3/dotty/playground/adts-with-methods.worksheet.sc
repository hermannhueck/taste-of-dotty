// ADT in Scala 3 Syntax with methods

enum Tree[+T]:
  case Leaf(elem: T)
  case Node(left: Tree[T], right: Tree[T])
  def count: Int = this match
    case Leaf(_) => 1
    case Node(left, right) => left.count + right.count

import Tree.*

val tree: Tree[Int] = Node(Leaf(1), Node(Leaf(2), Leaf(3)))
val count = tree.count
