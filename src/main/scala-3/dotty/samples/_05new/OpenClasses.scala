package dotty.samples._05new

object OpenClasses {

  // An open modifier on a class signals that the class is planned for extensions. Example:

  // Writer.scala
  open class Writer[T]:

    /** Sends to stdout, can be overridden */
    def send(x: T): Unit = println(x)
  
    /** Send all arguments using `send` */
    def sendAll(xs: T*) = xs.foreach(send)


  // EncryptedWriter.scala
  trait Encryptable[T]:
    def encrypt(t: T): T

  class EncryptedWriter[T: Encryptable] extends Writer[T]:
    override def send(x: T) = super.send(summon[Encryptable[T]].encrypt(x))

  
  // Details

  // - open is a soft modifier. It is treated as a normal identifier unless it is in modifier position.
  // - An open class cannot be final or sealed.
  // - Traits or abstract classes are always open, so open is redundant for them.

  // Relationship with sealed

  // A class that is neither abstract nor open is similar to a sealed class: it can still be extended,
  // but only in the same compilation unit. The difference is what happens if an extension of the class
  // is attempted in another compilation unit. For a sealed class, this is an error,
  // whereas for a simple non-open class, this is still permitted provided the adhocExtensions feature is enabled,
  // and it gives a warning otherwise.
}
