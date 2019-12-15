package dotty.samples.context

import scala.concurrent.ExecutionContext
import java.util.concurrent.atomic.AtomicInteger

// Implicit functions are functions with (only) implicit parameters.
// Their types are implicit function types. Here is an example of an implicit function type:

type Executable[T] = (given ExecutionContext) => T

// An implicit function is applied to synthesized arguments,
// in the same way a method with a given clause is applied. For instance:

given ec: ExecutionContext = ExecutionContext.global

def f(x: Int): Executable[Int] = {
  val result: AtomicInteger = AtomicInteger(0)
  def runOnEC(given ec: ExecutionContext) =
    ec.execute(() => result.set(x * x)) // execute a Runnable
    Thread.sleep(100L) // wait for the Runnable to be executed
    result.get
  runOnEC
}

def g(exec: Executable[Int]) =
  exec // just execute the Executable (EC is provided implicitly)


@main def runIFT(): Unit =
  
  import util._
  
  println(lineStart())

  val res1 = f(2)(given ec)   // ExecutionContext passed explicitly
  val res2 = f(2)             // ExecutionContext resolved implicitly
  
  println(res1) //=> 4
  println(res2) //=> 4

  val res3 = g(22)      // is expanded to g((given ev) => 22)
  val res4 = g(f(22))   // is expanded to g((given ev) => f(2)(given ev))
  val res5 = g((given ctx) => f(22)(given ctx)) // is left as it is
  val res6 = f(22) // is left as it is
  
  println(res3) //=> 22
  println(res4) //=> 484 (result of 22*22)
  println(res5) //=> 484 (result of 22*22)
  println(res6) //=> 484 (result of 22*22)

  println(lineEnd())
