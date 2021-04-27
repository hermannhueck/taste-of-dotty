val x = 42

if x < 0 then -x else x

if x < 0 then
  "negative"
else if x == 0 then
  "zero"
else
  "positive"

var y = 42
def f(x: Int): Int = x - 10

while y >= 0 do y = f(y)

while
  y >= 0
do
  y = f(y)

val xs = List(1, 2, 3)
val ys = List(10, 20, 30)

// for ... do ...

for x <- xs if x > 0
do println(x * x)

for
  x <- xs
  y <- ys
do
  println(x + y)

// for ... yield ...

for x <- xs if x > 0
yield x * x

for
  x <- xs
  y <- ys
yield x + y
