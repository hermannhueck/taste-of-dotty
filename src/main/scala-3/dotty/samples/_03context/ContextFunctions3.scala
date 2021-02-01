package dotty.samples._03context

import scala.collection.mutable.ArrayBuffer

object ContextFunctions3 {

  class Table:
    val rows = new ArrayBuffer[Row]
    def add(r: Row): Unit = rows += r
    override def toString = rows.mkString("Table(", ", ", ")")

  class Row:
    val cells = new ArrayBuffer[Cell]
    def add(c: Cell): Unit = cells += c
    override def toString = cells.mkString("Row(", ", ", ")")

  case class Cell(elem: String)

  def table(init: Table ?=> Unit) =
    given t: Table = Table()
    init
    t

  def row(init: Row ?=> Unit)(using t: Table) =
    given r: Row = Row()
    init
    t.add(r)

  def cell(str: String)(using r: Row) =
    r.add(new Cell(str))

  val t = table {
    row {
      cell("top left")
      cell("top right")
    }
    row {
      cell("bottom left")
      cell("bottom right")
    }
  }

}