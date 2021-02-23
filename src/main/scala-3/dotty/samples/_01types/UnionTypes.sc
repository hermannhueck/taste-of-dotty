import dotty.samples.types.*

val name = UserName("Eve")

val password = Password(123)

if (true) name else password
// val res0: Object & Product & Serializable = ...

val either: Password | UserName =
  if (true) name else password
// val either: dotty.samples.Password | dotty.samples.UserName = ...
