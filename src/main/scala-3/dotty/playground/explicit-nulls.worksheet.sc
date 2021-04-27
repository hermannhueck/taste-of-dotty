val x: String = "xFoo"
val y: String | Null = "yBar"

// x == null       // error: Values of types String and Null cannot be compared with == or !=
// x eq null       // error
// "hello" == null // error

y == null // ok
y == x // ok

(x: String | Null) == null // ok
(x: Any) == null // ok

// Working with Null:
// The extension method 'nn' casts away nullability.
val strOrNull: String | Null = "foo"
val str: String = strOrNull.nn
