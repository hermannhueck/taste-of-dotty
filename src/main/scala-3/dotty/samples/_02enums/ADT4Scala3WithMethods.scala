package dotty.samples._02enums

enum Tree4[+T]:
  case Leaf4(elem: T)
  case Node4(left: Tree4[T], right: Tree4[T])
  def count: Int = this match
    case Leaf4(_) => 1
    case Node4(left, right) => left.count + right.count

import Tree4.*

val tree4: Tree4[Int] = Node4(Leaf4(1), Node4(Leaf4(4), Leaf4(4)))
val count = tree4.count

import util.*

@main def printTree4: Unit =
  println(line())
  println(tree4.toString.blue)
  println(s"count = $count".red)
  println(line())
