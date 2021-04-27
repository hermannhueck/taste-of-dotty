// ADT in Scala 3 Syntax

enum Tree[+T]:
  case Leaf(elem: T) extends Tree[T]
  case Node(left: Tree[T], right: Tree[T]) extends Tree[T]

import Tree.*

val tree: Tree[Int] =
  Node(Leaf(1), Node(Leaf(2), Leaf(3)))
