// ADT in Scala 2 Syntax

sealed trait Tree[+T]
object Tree {
  case class Leaf[T](elem: T) extends Tree[T]
  case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
}

import Tree.*

val tree: Tree[Int] =
  Node(Leaf(1), Node(Leaf(2), Leaf(3)))
