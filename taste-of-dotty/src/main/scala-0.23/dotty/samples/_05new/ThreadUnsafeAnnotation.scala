package dotty.samples._05new

object ThreadUnsafeAnnotation {

  // A new annotation @threadUnsafe can be used on a field which defines a lazy val.
  // When this annotation is used, the initialization of the lazy val will use a faster mechanism
  // which is not thread-safe.

  import scala.annotation.threadUnsafe

  class Hello {
    @threadUnsafe lazy val x: Int = 1
  }
  
}
