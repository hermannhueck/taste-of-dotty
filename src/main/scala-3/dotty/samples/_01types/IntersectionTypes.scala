package dotty.samples._01types

trait Resettable:
  def reset(): this.type

trait Growable[T]:
  def add(x: T): this.type

type ResetGrowable[T] =
  Resettable & Growable[T]

class MyClass(var x : Int = 0) extends Resettable, Growable[Int]:
  def reset() =
    x = 0
    this
  def add(x: Int) =
    this.x += x
    this

def f(x: ResetGrowable[Int]) =
  x.reset()
  x.add(-21)

import util.*
import scala.util.chaining.*

@main def testIntersect: Unit =
  printLine()
  val obj = new MyClass(42) // 42
  obj.x.toString.green pipe println
  obj.reset() // 0
  obj.x.toString.green pipe println
  obj.add(10) // 10
  obj.x.toString.green pipe println
  f(obj) // -21
  obj.x.toString.green pipe println
  printLine()
