package samples._04meta

/*
  See:
  https://dotty.epfl.ch/docs/reference/metaprogramming/erased-terms.html
 */
object ErasedTerms {

  /*
  // Why erased terms?

  sealed trait State
  final class On extends State
  final class Off extends State

  @scala.annotation.implicitNotFound("State is must be Off")
  class IsOff[S <: State]
  object IsOff {
    implicit def isOff: IsOff[Off] = new IsOff[Off]
  }

  class Machine[S <: State] {
    def turnedOn(implicit ev: IsOff[S]): Machine[On] = new Machine[On]
  }

  val m = new Machine[Off]
  m.turnedOn
  // m.turnedOn.turnedOn // ERROR
  //                   ^
  //                   State is must be Off

  // How to define erased terms?

  trait Ev

  def methodWithErasedEv(erased ev: Ev): Int = 42

  val lambdaWithErasedEv: (erased Ev) => Int = {
   (erased ev: Ev) => 42
  }

   // erased parameters will not be usable for computations, though they can be used as arguments to other erased parameters.

   def methodWithErasedInt1(erased i: Int): Int =
     42
     // i + 42 // ERROR: can not use i

  def methodWithErasedInt2(erased i: Int): Int =
    methodWithErasedInt1(i) // OK

  // Not only parameters can be marked as erased, val and def can also be marked with erased.
  // These will also only be usable as arguments to erased parameters.

  erased val erasedEvidence: Ev = new Ev {}
  methodWithErasedEv(erasedEvidence)

  // What happens with erased values at runtime?

  // As erased are guaranteed not to be used in computations, they can and will be erased.

  // becomes def methodWithErasedEv(): Int at runtime
  // def methodWithErasedEv(erased ev: Ev): Int = ...

  def evidence1: Ev = new Ev {}
  erased def erasedEvidence2: Ev = new Ev {} // does not exist at runtime
  erased val erasedEvidence3: Ev = new Ev {} // does not exist at runtime

  // evidence1 is not evaluated and no value is passed to methodWithErasedEv
  methodWithErasedEv(evidence1)

  // State machine with erased evidence example

  object StateMachineWithErased {

    import scala.annotation.implicitNotFound

    sealed trait State
    final class On extends State
    final class Off extends State

    @implicitNotFound("State is must be Off")
    class IsOff[S <: State]
    object IsOff {
      // def isOff will not be called at runtime for turnedOn, the compiler will only require that this evidence exists
      implicit def isOff: IsOff[Off] = new IsOff[Off]
    }

    @implicitNotFound("State is must be On")
    class IsOn[S <: State]
    object IsOn {
      // erased val isOn will not exist at runtime, the compiler will only require that this evidence exists at compile time
      erased implicit val isOn: IsOn[On] = new IsOn[On]
    }

    class Machine[S <: State] private {
      // ev will disappear from both functions
      def turnedOn(given erased ev: IsOff[S]): Machine[On] = new Machine[On]
      def turnedOff(given erased ev: IsOn[S]): Machine[Off] = new Machine[Off]
    }

    object Machine {
      def newMachine(): Machine[Off] = new Machine[Off]
    }

    object Test {
      def main(args: Array[String]): Unit = {
        val m = Machine.newMachine()
        m.turnedOn
        m.turnedOn.turnedOff

        // m.turnedOff
        //            ^
        //            State is must be On

        // m.turnedOn.turnedOn
        //                    ^
        //                    State is must be Off
      }
    }
  }

  // Note that in Inline we discussed erasedValue and inline matches. erasedValue is implemented with erased,
  // so the state machine above can be encoded as follows:

  object StateMachineWithErasedValue {

    import scala.compiletime.*

    sealed trait State
    final class On extends State
    final class Off extends State

    class Machine[S <: State] {
      inline def turnOn() <: Machine[On] = inline erasedValue[S] match {
        case _: Off  => new Machine[On]
        case _: On   => error("Turning on an already turned on machine")
      }
      inline def turnOff() <: Machine[Off] = inline erasedValue[S] match {
        case _: On  => new Machine[Off]
        case _: Off   => error("Turning off an already turned off machine")
      }
    }

    object Machine {
      def newMachine(): Machine[Off] = {
        println("newMachine")
        new Machine[Off]
      }
    }

    object Test {
      val m = Machine.newMachine()
      val on = m.turnOn()
      val off = m.turnOn().turnOff()
      // m.turnOn().turnOn() // error: Turning on an already turned on machine
    }
  }
   */
}
