package dotty.samples._02enums

enum Tree2[+T]:
  case Leaf2(elem: T) extends Tree2[T]
  case Node2(left: Tree2[T], right: Tree2[T]) extends Tree2[T]

import Tree2._

val tree2: Tree2[Int] = Node2(Leaf2(1), Node2(Leaf2(2), Leaf2(3)))

import util._

@main def printTree2: Unit =
  println(tree2.toString.green.boxed())