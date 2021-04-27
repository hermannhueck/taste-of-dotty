package dotty.playground

trait Resettable:
  def reset(): Unit

trait Growable[T]:
  def add(x: T): Unit

def f(x: Resettable & Growable[String]) =
   x.reset()
   x.add("first")

class MyClass(var x : String = "") extends Resettable, Growable[String]:
  def reset(): Unit =
    x = ""
  def add(s: String): Unit =
    x += s
  override def toString =
    s"[$x]"

val obj: MyClass = MyClass("foo") // [foo]

import scala.util.chaining.*
import util.*
    
@main def runIntersectionTypesExample: Unit =

  line(80).green pipe println

  println(obj)
  obj.reset() // []
  println(obj)
  obj.add("bar") // [bar]
  println(obj)
  obj.add("baz") // [barbaz]
  println(obj)
  f(obj) // [first]
  println(obj)
  
  line(80).green pipe println
