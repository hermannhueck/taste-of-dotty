package dotty.samples.types

object DependentFunctionTypes {

  trait Entry { type Key; val key: Key }

  def extractKey(e: Entry): e.Key = e.key          // a dependent method
  
  val extractor: (e: Entry) => e.Key = extractKey  // a dependent function value
  //            ║   ⇓ ⇓ ⇓ ⇓ ⇓ ⇓ ⇓   ║
  //            ║     Dependent     ║
  //            ║   Function Type   ║
  //            ╚═══════════════════╝

  val intEntry = new Entry { type Key = Int; val key = 42 }
  val stringEntry = new Entry { type Key = String; val key = "foo" }


  @main def runExtractor: Unit =

    import util._
    import scala.util.chaining._
    import scala.language.implicitConversions

    lineStart() pipe println

    val intKey1 = extractKey(intEntry) tap println
    val intKey2 = extractor(intEntry) tap println
    val stringKey1 = extractKey(stringEntry) tap println
    val stringKey2 = extractor(stringEntry) tap println

    assert(intKey1 == intKey2)
    assert(stringKey1 == stringKey2)

    lineEnd() pipe println
}