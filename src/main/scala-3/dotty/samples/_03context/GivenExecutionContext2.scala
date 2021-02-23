package dotty.samples._03context

import scala.concurrent.{Future, Await, ExecutionContext}
import scala.concurrent.duration.*
import scala.util.{Try, Success, Failure}

import scala.util.chaining.*
import util.*

@main def GivenExecutionContext2: Unit =

  printStartLine()

  // import ExecutionContext.Implicits.global
  import ExecutionContext.Implicits.{given ExecutionContext}

  def someComputation(): Int =
    println("computing Int value asynchronously ...")
    Thread.sleep(3000L)
    42

  val future: Future[Int] = Future { someComputation() }

  future onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => println(throwable)
  }

  Thread.sleep(4000L)
  
  printEndLine()
