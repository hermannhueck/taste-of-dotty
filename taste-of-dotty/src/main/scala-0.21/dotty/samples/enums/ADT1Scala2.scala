package dotty.samples.enums

sealed trait Tree1[T]
object Tree1 {
  case class Leaf1[T](elem: T) extends Tree1[T]
  case class Node1[T](left: Tree1[T], right: Tree1[T]) extends Tree1[T]
}

import Tree1._

val tree1: Tree1[Int] = Node1(Leaf1(1), Node1(Leaf1(1), Leaf1(3)))

import util._

@main def printTree1: Unit =
  println(tree1.toString.green.boxed())