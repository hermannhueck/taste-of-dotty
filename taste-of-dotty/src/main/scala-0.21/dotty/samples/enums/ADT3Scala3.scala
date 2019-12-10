package dotty.samples.enums

enum Tree3[T]
  case Leaf3(elem: T)
  case Node3(left: Tree3[T], right: Tree3[T])

import Tree3._

val tree3: Tree3[Int] = Node3(Leaf3(1), Node3(Leaf3(3), Leaf3(3)))

import util._

@main def printTree3: Unit =
  println(tree3.toString.green.boxed())