package dotty.samples._03context

object GivenByNameParameters extends App:

  trait Codec[T]:
    def write(x: T): Unit

  given intCodec as Codec[Int]:
    def write(x: Int): Unit = println(s"x = $x")

  given optionCodec[T](using ev: => Codec[T]) as Codec[Option[T]]: // given param ev is evaluated lazily
    def write(xo: Option[T]) = xo match
      case Some(x) => ev.write(x)
      case None =>

  val s = summon[Codec[Option[Int]]]

  s.write(Some(33))
  s.write(None)
