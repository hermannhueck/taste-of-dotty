package dotty.samples.context

import scala.concurrent.{Future, Await, ExecutionContext}
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}

import scala.util.chaining._
import util._

object GivenExecutionContext1 extends App {

  line().green pipe println

  implicit val ec: ExecutionContext = ExecutionContext.global // Scala 2
  // given ec: ExecutionContext = ExecutionContext.global // Scala 3 (variable ec can be omitted)

  def someComputation(): Int = {
    println("computing Int value asynchronously ...")
    Thread.sleep(3000L)
    42
  }

  val future: Future[Int] = Future { someComputation() }

  future onComplete {
    case Success(value) => s"value = ${value.toString}" pipe println
    case Failure(throwable) => s"exception = ${throwable.getMessage}" pipe println
  }

  Thread.sleep(4000L)
  
  line().green pipe println
}