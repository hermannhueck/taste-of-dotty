package dotty.samples._06changed

object StrucutralTypes {

  object StructuralTypeExample:

    case class Record(elems: (String, Any)*) extends Selectable:
      def selectDynamic(name: String): Any = elems.find(_._1 == name).get._2
  
    type Person = Record {
      val name: String
      val age: Int
    }
  
    def main(args: Array[String]): Unit =
      val person = Record("name" -> "Emma", "age" -> 42).asInstanceOf[Person]
      println(s"${person.name} is ${person.age} years old.")
      // Prints: Emma is 42 years old.

  // New instances of Selectable can be defined to support means of access other than Java reflection,
  // which would enable usages such as the database access example given at the beginning of this document.

}

