package dotty.samples.context

object GivenByNameParameters

  trait Codec[T]
    def write(x: T): Unit

  given intCodec: Codec[Int] = ???

  given optionCodec[T](given ev: => Codec[T]): Codec[Option[T]] // given param ev is evaluated lazily
    def write(xo: Option[T]) = xo match
      case Some(x) => ev.write(x)
      case None =>

  val s = summon[Codec[Option[Int]]]

  s.write(Some(33))
  s.write(None)
