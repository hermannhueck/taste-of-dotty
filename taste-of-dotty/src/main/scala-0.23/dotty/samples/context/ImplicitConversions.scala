package dotty.samples.context

case class Token(str: String)

object ImplicitConversion1:
  given Conversion[String, Token]:
    def apply(str: String): Token = Token(str)


object ImplicitConversion2:
  given Conversion[String, Token] = Token(_)


object ImplicitConversion3Scala2 {

  import scala.language.implicitConversions

  implicit def stringToToken(str: String): Token = Token(str)
}
