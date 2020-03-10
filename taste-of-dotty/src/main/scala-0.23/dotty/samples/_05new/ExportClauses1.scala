package dotty.samples._05new

object ExportClauses1 extends App {

  class BitMap
  class InkJet

  class Printer {
    type PrinterType
    def print(bits: BitMap): Unit = println("printing BitMap ...")
    def status: List[String] = ???
  }

  class Scanner {
    def scan(): BitMap = {println("scanning BitMap ...")
      new BitMap
    }
    def status: List[String] = ???
  }

  class Copier {
    private val printUnit = new Printer { type PrinterType = InkJet }
    private val scanUnit = new Scanner

    export scanUnit.scan
    export printUnit.{status => _, _}

    def status: List[String] = printUnit.status ++ scanUnit.status
  }

  // The two export clauses define the following export aliases in class Copier:

  // final def scan(): BitMap            = scanUnit.scan()
  // final def print(bits: BitMap): Unit = printUnit.print(bits)
  // final type PrinterType              = printUnit.PrinterType

  // They can be accessed inside Copier as well as from outside:

  val copier = new Copier
  copier.print(copier.scan())
}