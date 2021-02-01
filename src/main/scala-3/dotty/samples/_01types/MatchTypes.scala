package dotty.samples._01types

object MatchTypes {

  // simple match type
  type Elem[X] = X match
    case String => Char
    case Array[t] => t
    case Iterable[t] => t

  // proofs
  summon[Elem[String]       =:=  Char]
  summon[Elem[Array[Int]]   =:=  Int]
  summon[Elem[List[Float]]  =:=  Float]
  summon[Elem[Nil.type]     =:=  Nothing]

  // recursive match type
  type LeafElem[X] = X match {
    case String => Char
    case Array[t] => LeafElem[t]
    case Iterable[t] => LeafElem[t]
    case AnyVal => X
  }

  // recursive match type with upper bound 'Tuple'
  type Concat[Xs <: Tuple, +Ys <: Tuple] <: Tuple = Xs match {
    case Unit => Ys
    case x *: xs => x *: Concat[xs, Ys]
  }
}