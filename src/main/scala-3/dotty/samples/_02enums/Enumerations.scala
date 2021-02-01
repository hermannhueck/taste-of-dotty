package dotty.samples._02enums

object Enumerations {

  // An enumeration is used to define a type consisting of a set of named values.

  enum Color:
    case Red, Green, Blue
      
  // Parameterized enums

  enum Color2(val rgb: Int):
    case Red   extends Color2(0xFF0000)
    case Green extends Color2(0x00FF00)
    case Blue  extends Color2(0x0000FF)

  // Methods defined for enums

  val red: Color = Color.Red
  val ord = red.ordinal // 0

  val blue: Color = Color.valueOf("Blue")
  val colors: Array[Color] = Color.values

  // User-defined members of enums
  // t is possible to add your own definitions to an enum.

  enum Planet(mass: Double, radius: Double):
    private final val G = 6.67300E-11
    def surfaceGravity = G * mass / (radius * radius)
    def surfaceWeight(otherMass: Double) =  otherMass * surfaceGravity
  
    case Mercury extends Planet(3.303e+23, 2.4397e6)
    case Venus   extends Planet(4.869e+24, 6.0518e6)
    case Earth   extends Planet(5.976e+24, 6.37814e6)
    case Mars    extends Planet(6.421e+23, 3.3972e6)
    case Jupiter extends Planet(1.9e+27,   7.1492e7)
    case Saturn  extends Planet(5.688e+26, 6.0268e7)
    case Uranus  extends Planet(8.686e+25, 2.5559e7)
    case Neptune extends Planet(1.024e+26, 2.4746e7)

  // It is also possible to define an explicit companion object for an enum:

  object Planet:
    def main(args: Array[String]) =
      val earthWeight = args(0).toDouble
      val mass = earthWeight / Earth.surfaceGravity
      for
        p <- values
      do
        println(s"Your weight on $p is ${p.surfaceWeight(mass)}")

  // Compatibility with Java Enums

  enum Color3 extends java.lang.Enum[Color3]:
    case Red, Green, Blue
}