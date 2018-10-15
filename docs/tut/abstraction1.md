# Pure functions

In functional programming we say that functions have some special
features.

# They're values

Functions can be treated as any other value in your program. They can
be assigned to `val`s, returned from methods, taken as parameters...

# Purity

##

They're pure, meaning that they perform no side effects

##

given the same input, always return the same output

## Side effects

- Throwing exceptions
- IO
- mutating variables
- Random

# Referential transparency

They're referentially transparent.  We can safely substitute any
function call with its return value and it won't have any effect in
the overall program.

# Totality

##

They're total.  Functions should operate on all values from it's input
type. If they fail with an input, they're not total.

##

When a function is not total, we say it's partial.

# Examples

#

##

```tut:silent
def sum(a: Int, b: Int): Int = a + b
```

Is this pure?

<p class="fragment">YES!</p>

##

```tut:silent
def sum(a: Int, b: Int): Int = {
  println(s"summing $a + $b")
  a + b
}
```

Is this pure?

<p class="fragment">Nope, it's side effectful!</p>

##

```tut:invisible
def launchMissiles(): Unit = ???
```

```tut:silent
def operation(): Unit = {
  launchMissiles()
  ()
}
```

Is this pure?

<p class="fragment">Nope, it's side effectful!</p>

##

```tut:silent
def findPhone(name: String): Option[String] = None
```

Is this pure?

<p class="fragment">Yes!</p>

##

```tut:invisible
object db {
  def findUser(id: Int): Option[String] = None
}
```

```tut:silent
def findUser(id: Int): String = {
  db.findUser(id) match {
    case Some(x) => x
	case None => throw new Exception("No user found")
  }
}
```

Is this pure?

<p class="fragment">Nope, it's side effectful!</p>

##

```tut:silent
def toString(id: Int): String = id match {
  case 1 => "one"
}
```

Is this pure?

<p class="fragment">Nope, it's partial!</p>

# Recap

##

Yesterday we implemented a binary tree

##

```tut:silent
sealed trait Tree[A]
case class Empty[A]() extends Tree[A]
case class Node[A](l: Tree[A], a: A, r: Tree[A]) extends Tree[A]
```

##

```tut
val myTree = Node(Node(Empty(),2,Empty()),1,Node(Node(Empty(),4,Empty()),3,Node(Node(Empty(),6,Empty()),5,Node(Empty(),7,Empty()))))
```

##

Abstraction is the ultimate goal of functional programming.  If you
see the implementations we ended up creating for yesterday's
exercises, you'll see that there's a pattern:

##

```tut
def height[A](tree: Tree[A]): Int = tree match {
  case Empty() => 0
  case Node(l, _, r) => 1 + (height(l).max(height(r)))
}

height(myTree)
```

##

```tut
def sum(tree: Tree[Int]): Int = tree match {
  case Empty() => 0
  case Node(l, x, r) => x + sum(l) + sum(r)
}

sum(myTree)
```

##

```tut
def count[A](tree: Tree[A]): Int = tree match {
  case Empty() => 0
  case Node(l, _, r) => 1 + count(l) + count(r)
}

count(myTree)
```

##

```tut
def toStringNodes(tree: Tree[Int]): Tree[String] = tree match {
  case Empty() => Empty()
  case Node(l, x, r) => Node(
    toStringNodes(l),
	x.toString,
	toStringNodes(r))
}

toStringNodes(myTree)
```

##

```tut
def squared(tree: Tree[Int]): Tree[Int] = tree match {
  case Empty() => Empty()
  case Node(l, x, r) => Node(
    squared(l),
	x * x,
	squared(r))
}

squared(myTree)
```

# Exercise 3

##

Can you create a higher order function with the common parts of
previous functions?

## Identifying common functions

The pattern we've identified here is called `fold`, or more
specifically, `catamorphism`.

Folds consume structures and create values out of them.

##

![tree](img/tree.png)

##

![fold](img/catamorphism.png)


## fold

```tut
def fold[A, B](tree: Tree[A])(onEmpty: B, onNode: (B, A, B) => B): B = tree match {
  case Empty() => onEmpty
  case Node(left, a, right) => onNode(
    fold(left)(onEmpty, onNode),
	a,
	fold(right)(onEmpty, onNode)
	)
}
```

##

Now that we have created the `fold` function, let's reimplement the other functions based on it!

##

```tut
def heightWithFold[A](tree: Tree[A]): Int = fold(tree)(0, { (l: Int, _: A, r: Int) =>
  1 + (l.max(r))
})

heightWithFold(myTree)
```

##

```tut
def sumWithFold(tree: Tree[Int]): Int = fold(tree)(0, { (l: Int, a: Int, r: Int) =>
  l + a + r
})

sumWithFold(myTree)
```

##

```tut
def countWithFold[A](tree: Tree[A]): Int = fold(tree)(0, { (l: Int, _: A, r: Int) =>
  1 + l + r
})

countWithFold(myTree)
```

##

```tut
def toStringNodesWithFold(tree: Tree[Int]): Tree[String] = fold(tree)(Empty[String](), { (l: Tree[String], a: Int, r: Tree[String]) =>
  Node[String](l, a.toString, r)
})

toStringNodesWithFold(myTree)
```

##


```tut
def squaredWithFold(tree: Tree[Int]): Tree[Int] = fold(tree)(Empty[Int](), { (l: Tree[Int], a: Int, r: Tree[Int]) =>
  Node[Int](l, a * a, r)
})

squaredWithFold(myTree)
```

##

If we look closer at our last abstractions using fold, we can notice
another pattern in the last two functions. They're transforming the
value of the nodes, but leaving the structure intact.

That's a `map`! and we can implement it based on `fold`!

## map

```tut
def map[A, B](tree: Tree[A])(fn: A => B): Tree[B] = fold(tree)(Empty[B](), { (l: Tree[B], a: A, r: Tree[B]) =>
  Node[B](l, fn(a), r)
})
```

##

And finally, implement those based on `map`!

##

```tut
def toStringNodesWithMap(tree: Tree[Int]): Tree[String] = map(tree)(_.toString)

toStringNodesWithMap(myTree)
```

##

```tut
def squaredWithMap(tree: Tree[Int]): Tree[Int] = map(tree)(x => x*x)

squaredWithMap(myTree)
```

# Identifying functional patterns

#

##

That's all we've done in these last examples, find repetitions and try
to abstract them.  That's what FP is about!

##

There are some well know abstractions we should be aware of

# fold

##

`fold` consumes a structure `F[_]` and produces a value out of it

```tut:silent
def fold[F[_], A, B](f: F[A])(onEmpty: B, onNode: (B, A, B) => B): B = ???
```

# map

##

`map` transforms each element given a function `A => B`

```tut:silent
def map[F[_], A, B](f: F[A])(fn: A => B): F[B] = ???
```

# flatMap

##

`flatMap` is similar to `map`, but the lambda we pass to it returns a
`F[B]` instead of a `B`

```tut:silent
def flatMap[F[_], A, B](f: F[A])(fn: A => F[B]): F[B] = ???
```

# filter

##

`filter` returns a new structure `F[_]` with elements that doesn't adjust to a
predicate `fn: A => Boolean` filtered out.

```tut:silent
def filter[F[_], A](f: F[A])(fn: A => Boolean): F[A] = ???
```

# Find

##

`find` returns the first element in a structure `F[_]` that matches a predicate.

```tut:silent
def filter[F[_], A](f: F[A])(fn: A => Boolean): Option[A] = ???
```

# functional datatypes

##

There are some datatypes in the scala standard library that help a lot
with common tasks.

#

## Option

##

Option is used when something may be not present. Use it whenever you'd use null `null`.

##

```tut:invisible
val hostDefined = false
val getHost = ""
```

With nulls:

```tut:silent
def httpConnection: String = {
  if (hostDefined) {
    getHost
  } else {
    null
  }
}
```

##

With option:

```tut:silent
def httpConnection: Option[String] = {
  if (hostDefined) {
    Some(getHost)
  } else {
    None
  }
}
```

##

You can construct options with its two constrcutors `Some` & `None`,
and you can also use the `Option` constructor, that will convert nulls
in `None` if they occur

#

## Try

##

Try captures exceptions and represents them as `Failure`s instead of
throwing them!

##

without `Try`:

```tut
def user: String = try {
  findUser(3)
} catch {
  case e: Exception =>
    throw e
}
```

##

with `Try`:

```tut
import scala.util.Try

def user: Try[String] = Try(findUser(3))
```

#

## Either

##

Either represents computations that can return two different values.
One of the many use cases for either is validations:

##

without either

```tut
case class ValidationError() extends Exception()

def validatePhone(phone: String): String = if (phone.length == 9) {
  phone
} else {
  throw ValidationError()
}
```

##

With either:

```tut
def validatePhone(phone: String): Either[ValidationError, String] =
  if (phone.length == 9) {
    Right(phone)
  } else {
    Left(ValidationError())
  }
```

#

## Future

##

Future represents computations detached from time.  You know that
those computations will happen, but do not when.  It's useful when you
have an expensive operation and don't want to block the current
thread.

##

without future

```tut:silent
val callDB: Runnable = new Runnable {
  def run(): Unit = {
    db.findUser(3)
  }
}

new Thread(callDB).start
```

##

with future

```tut:silent
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

val callDB: Future[String] = Future(findUser(3))
```

# Exercise 4
