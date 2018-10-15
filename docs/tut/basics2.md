# Scala basics 2

In this session we'll deepen our knowledge of pattern matching &
recursion!

# Pattern matching

##

Pattern matching is a technique used in scala (and other languages) to
compare values against shapes and conditions.  You can think of it
like a more powerful `switch` statement.

##

it's used the following way:

```tut:silent
val a: Int = 3

a match {
  case 3 => "it's three!"
  case _ => "it's not three!"
}
```

# Exhaustivity

##

Scala's pattern matching has an exhaustivity checker.  This means
that the compiler will warn if we forget to match against one of the
cases.

##

```tut:silent
sealed trait Color
case object Blue extends Color
case object Red extends Color
case class Other(name: String) extends Color
```

##

```tut:silent
val color: Color = Blue
```

##

WARNING:

```tut
color match {
  case Blue => println("it's blue!")
  case Other(x) => println(s"it's $x!")
}
```

# Destructuring

Destructuring allows us to query inner parts of an ADT

##

```tut:silent
sealed trait Vehicle
case class Car(brand: String, model: String, color: Color) extends Vehicle
case class Plane(brand: String, model: String, wingSpan: Int) extends Vehicle
```

##

```tut:silent
val vehicle: Vehicle = Car("Honda", "Accord", Red)
```

##

```tut
vehicle match {
  case Car(brand, model, Red) => s"it's a red $brand $model"
  case Car(brand, model, Blue) => s"it's a red $brand $model"
  case Car(brand, model, Other(colorName)) => s"it's a $colorName $brand $model"
  case Plane(brand, model, wingSpan) => s"it's a $brand $model with $wingSpan meter of wing span!"
}
```

# Guards

boolean conditions we want to check

##

given

```tut:silent
val plane: Vehicle = Plane("Boeing", "747", 47)
```

##

```tut
plane match {
  case Plane(brand, model, wingSpan) if wingSpan > 40 => s"it's a big $brand $model"
  case Plane(brand, model, wingSpan) if wingSpan <= 40 => s"it's a small $brand $model"
  case _ => s"it's not a plane..."
}
```

# Recursion

##

Recursion happens when a function calls itself.  It's the solution we
use in functional programming to the problems for which OOP uses
loops.

## Fibonacci sequence

Fibonacci sequence is an infinite in which every number is defined by
summing the two previous numbers.

## Fibonacci in Python

```python
def fib(num):
    a = 1
    b = 0
    temp = 0
    while(num >= 0):
        temp = a
        a = a+b
        b = temp
        num = num - 1
    return b
```

## Fibonacci in Scala

```tut:silent
def fib(num: Int): Int = num match {
  case 0 => 1
  case 1 => 1
  case x => fib(x - 1) + fib(x - 2)
}
```

##

Recursion is tightly coupled to pattern matching

##

```tut:silent
sealed trait List[A]
case class Empty[A]() extends List[A]
case class NonEmpty[A](head: A, tail: List[A]) extends List[A]
```

##

Let's see some common functions implemented recursively on a `List`!

```tut
val three = NonEmpty(1, NonEmpty(2, NonEmpty(3, Empty())))
```

##

```tut
def length[A](l: List[A]): Int = l match {
  case Empty() => 0
  case NonEmpty(x, xs) => 1 + length(xs)
}

length(three)
```

##

```tut
def sum(list: List[Int]): Int = list match {
  case Empty() => 0
  case NonEmpty(x, xs) => x + sum(xs)
}
```

# Exercise 2.1

Implement a generic binary tree data structure.

#

## Solution

```tut:silent
sealed trait Tree[A]
case class Empty[A]() extends Tree[A]
case class Node[A](l: Tree[A], a: A, r: Tree[A]) extends Tree[A]
```

# Exercise 2.2

create a function to calculate the height of a tree.

#

## Solution

```tut
def height[A](tree: Tree[A]): Int = tree match {
  case Empty() => 0
  case Node(l, _, r) => 1 + (height(l).max(height(r)))
}
```

# Exercise 2.3

Create a function that sums all the leaves of an Int tree.

#

## Solution


```tut
def sum(tree: Tree[Int]): Int = tree match {
  case Empty() => 0
  case Node(l, x, r) => x + sum(l) + sum(r)
}
```

# Exercise 2.4

Create a function that counts all the leaves in a tree

#

## Solution

```tut
def count[A](tree: Tree[A]): Int = tree match {
  case Empty() => 0
  case Node(l, _, r) => 1 + count(l) + count(r)
}
```

# Exercise 2.5

Create a function that transforms each element in a tree into it's
string representation

#

## Solution

```tut
def toStringNodes(tree: Tree[Int]): Tree[String] = tree match {
  case Empty() => Empty()
  case Node(l, x, r) => Node(
    toStringNodes(l),
	x.toString,
	toStringNodes(r))
}
```


# Exercise 2.6

Create a function that squares all elements in an Int tree

#

## Solution

```tut
def squared(tree: Tree[Int]): Tree[Int] = tree match {
  case Empty() => Empty()
  case Node(l, x, r) => Node(
    squared(l),
	x * x,
	squared(r))
}
```
