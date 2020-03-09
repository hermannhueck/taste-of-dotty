package dotty.samples.types

type Hash = Int
  
case class UserName(name: String)
case class Password(hash: Hash)

def help(id: UserName | Password): String =
  id match
    case UserName(name) => name
    case Password(hash) => hash.toString


import util._
import scala.util.chaining._
import scala.language.implicitConversions

@main def testUnion: Unit =

  printLine()

  val name: UserName = UserName("Eve") tap println
  val password: Password = Password(123) tap println
  val nameOrPw: UserName | Password =
    if true then name else password
  nameOrPw pipe println

  printLine()
