package dotty.samples._03context

import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicInteger

// Context functions are functions with (only) context parameters.
// Their types are context function types. Here is an example of a context function type:

type Executable[T] = ExecutionContext ?=> T

// Context functions are written using ?=> as the "arrow" sign.
// They are applied to synthesized arguments, in the same way 
// methods with context parameters are applied. For instance:

given ec: ExecutionContext = ExecutionContext.global

def f(x: Int): Executable[Int] = {
  val result: AtomicInteger = AtomicInteger(0)
  def runOnEC(using ec: ExecutionContext): Int =
    ec.execute(() => result.set(x * x)) // execute a Runnable
    Thread.sleep(100L) // just for demo: wait for the Runnable to be executed
    result.get
  runOnEC
}

def g(exec: Executable[Int]) =
  exec // just execute the Executable (EC is provided implicitly)


@main def ContextFunctions1(): Unit =
  
  import util._
  
  printStartLine()

  val res1: Int = f(2)(using ec)   // ExecutionContext passed explicitly
  val res2: Int = f(2)             // ExecutionContext resolved implicitly
  
  println(res1) //=> 4
  println(res2) //=> 4

  val res3 = g(22)      // is expanded to g((using ev) => 22)
  val res4 = g(f(22))   // is expanded to g((using ev) => f(2)(using ev))
  val res5 = g((ctx: ExecutionContext) ?=> f(3)) // is expanded to g((ctx: ExecutionContext) ?=> f(3)(using ctx))
  val res6 = g((ctx: ExecutionContext) ?=> f(3)(using ctx)) // is left as it is
  
  println(res3) //=> 22
  println(res4) //=> 484 (result of 22*22)
  println(res5) //=> 484 (result of 22*22)
  println(res6) //=> 484 (result of 22*22)

  printEndLine()
