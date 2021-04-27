// A polymorphic method:
def foo[A](xs: List[A]): List[A] = xs.reverse

// A polymorphic function value:
val bar: [A] => List[A] => List[A]
//       ^^^^^^^^^^^^^^^^^^^^^^^^^
//       a polymorphic function type
       = [A] => (xs: List[A]) => foo[A](xs)

// ... with type inferred
val bar2 = [A] => (xs: List[A]) => foo(xs)


// invocation of polymorphic method
foo[Int](List(1, 2, 3))
foo(List(1, 2, 3))

// invocation of polymorphic function
bar[Int](List(1, 2, 3))
bar(List(1, 2, 3))
bar2(List(1, 2, 3))
