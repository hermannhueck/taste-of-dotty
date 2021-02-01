package dotty.samples._03context

import scala.concurrent.{Future, Await, ExecutionContext}
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}

import scala.language.implicitConversions
import scala.util.chaining._
import util._

@main def GivenExecutionContext1: Unit =

  printStartLine()

  // implicit val ec: ExecutionContext = ExecutionContext.global // Scala 2
  given ec: ExecutionContext = ExecutionContext.global // variable ec can be omitted

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
