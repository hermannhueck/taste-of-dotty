package dotty.playground

import scala.concurrent.{Await, Future, ExecutionContext}
import scala.concurrent.duration.*
import scala.util.{Success, Failure}

@main def givenExecutionContextExample1(): Unit =

  // implicit val ec: ExecutionContext = ExecutionContext.global // Scala 2
  given ec: ExecutionContext = ExecutionContext.global // Scala 3: variable ec can be omitted

  def someComputation(): Int = {
    Thread.sleep(3000)
    3 + 2
  }
  val future: Future[Int] = Future { someComputation() }

  future onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => println(throwable)
  }

  Await.ready(future, Duration.Inf)
