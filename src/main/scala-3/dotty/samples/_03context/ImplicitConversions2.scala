package dotty.samples._03context

object ImplicitConversions2 {

  import scala.concurrent.Future

  trait HttpResponse
  trait StatusCode

  // The argument "magnet" type
  enum CompletionArg:
    case Error(s: String)
    case Response(f: Future[HttpResponse])
    case Status(code: Future[StatusCode])

  object CompletionArg:

    // conversions defining the possible arguments to pass to `complete`
    // these always come with CompletionArg
    // They can be invoked explicitly, e.g.
    //
    //   CompletionArg.fromStatusCode(statusCode)

    given fromString: Conversion[String, CompletionArg]               = Error(_)
    given fromFuture: Conversion[Future[HttpResponse], CompletionArg] = Response(_)
    given fromStatusCode: Conversion[Future[StatusCode], CompletionArg]   = Status(_)

  import CompletionArg.*

  def complete[T](arg: CompletionArg) = arg match
    case Error(s) => ???
    case Response(f) => ???
    case Status(code) => ???
}
