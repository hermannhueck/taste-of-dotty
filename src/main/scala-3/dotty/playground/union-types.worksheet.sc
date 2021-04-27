type Hash = Int

case class UserName(name: String)
case class Password(hash: Hash)

val name: UserName = UserName("Eve")
val password: Password = Password(123)

val nameOrPw1 =
  if true then name else password

val nameOrPw2: UserName | Password =
  if true then name else password
