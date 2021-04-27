package dotty.playground

object UnionTypes:
  type Hash = Int

  case class UserName(name: String)
  case class Password(hash: Hash)

  def help(id: UserName | Password): String =
    id match
      case UserName(name) => name
      case Password(hash) => hash.toString

  val name: UserName = UserName("Eve")
  val password: Password = Password(123)

  val nameOrPw: UserName | Password =
    if true then name else password

import scala.util.chaining.*
import util.*
import UnionTypes.*
    
@main def runUnionTypesExample: Unit =

  line(80).green pipe println

  println(nameOrPw)
  println(help(Password(123456)))
  
  line(80).green pipe println
