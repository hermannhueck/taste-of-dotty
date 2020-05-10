package dotty.samples._06changed

object NumericLiterals {

  val x: Long = -10_000_000_000
  val y: BigInt = 0x123_abc_789_def_345_678_901
  val z: BigDecimal = 110_222_799_799.99

  (y: BigInt) match {
    case 123_456_789_012_345_678_901 =>
  }
}


case class BigFloat(mantissa: BigInt, exponent: Int) {
  override def toString = s"${mantissa}e${exponent}"
}

object BigFloat {

  import scala.util.FromDigits

  def apply(digits: String): BigFloat = {

    val (mantissaDigits, givenExponent) =
      digits.toUpperCase.split("E") match
        case Array(mantissaDigits, edigits) =>
          val expo =
            try FromDigits.intFromDigits(edigits.nn)
            catch {
              case ex: FromDigits.NumberTooLarge =>
                throw FromDigits.NumberTooLarge(s"exponent too large: $edigits")
            }
          (mantissaDigits, expo)
        case Array(mantissaDigits) =>
          (mantissaDigits, 0)

    val (intPart, exponent) =
      mantissaDigits.toString.split('.') match
        case Array(intPart, decimalPart) =>
          (intPart ++ decimalPart, givenExponent - decimalPart.length)
        case Array(intPart) =>
          (intPart, givenExponent)

    BigFloat(BigInt(intPart), exponent)
  }

  // given FromDigits : FromDigits.Floating[BigFloat] {
  //   def fromDigits(digits: String) = apply(digits)
  // }
}