// See also: https://heikoseeberger.rocks/2020/01/07/2020-01-07-dotty-4/

final case class Foo()

Foo() == Foo()
Foo() == Option(Foo())

import scala.language.strictEquality

Foo() == Foo()
Foo() == Option(Foo())

given CanEqual[Foo, Foo] = CanEqual.derived

Foo() == Foo()
Foo() == Option(Foo())

final case class Bar() derives CanEqual

Bar() == Bar()
Bar() == Option(Bar())

Foo() == Bar()
Bar() == Foo()

given CanEqual[Foo, Bar] = CanEqual.derived
given CanEqual[Bar, Foo] = CanEqual.derived

Foo() == Bar()
Bar() == Foo()

// The Scala standard library provides
// some of these bidirectional Eql instances
// for numeric types:

42 == 42L
42 == 42.0

// _Seq[T]_ can be compared to _Seq[T]_ (or any sub type) if their element types can be compared.
// _Set[T]_ can be compared to _Set[T]_ (or any sub type) if their element types can be compared.
// Any subtype of _AnyRef_ can be compared with _Null_.

List(1, 2, 3) == Vector(1, 2, 3)
scala.collection.immutable.HashSet(1, 2, 3) ==
    scala.collection.immutable.TreeSet(1, 2, 3)

Foo() == null


// Strict/multiversial equality is opt-in.
// if unwanted (for backward compatibility)
// just omit the language feature
// "scala.language.strictEquality"
// and the compiler flag
// -language:strictEquality
//
// In this case the compiler provides
// an CanEqual instance to compare any two types:
//
def eqlAny[L, R]: CanEqual[L, R] = CanEqual.derived
given CanEqual[Any, Any] = eqlAny

Foo() == Foo()
Foo() == Option(Foo())
