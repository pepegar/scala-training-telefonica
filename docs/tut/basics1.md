# Scala Basics

## Scala?

- Object Oriented/Functional Language
- Static typing
- Type inference
- Functional programming capabilities

## Why object oriented?

- subtyping
- everything's an object
- inheritance

## Why functional?

- Functions are _first class citizens_
- Pattern matching
- Algebraic Data Types
- Higher kinded types

_This is the cool part, and what we'll be learning about_

# The type system

##

```tut:fail
val a: Int = "hola!"
```

##

A type system checks the types of our program to be sure they make
sense.

# Static typing

##

Static typing means that once a variable has a type, it cannot change.
The opposite of static typing is dynamic typing, as in Python, JS...

## Python

```python
a = "patata"
a = 3
```

## JS

```javascript
var a = "patata"
a = 3
```

## Scala

```tut:silent
var a = "patata"
```

```tut:fail
a = 3
```

# Type inference

##

Normally, the compiler will be able to guess what type our values have
even if we don't specify it.

##

```tut
val hola = "hola"
val three = 3
```

##

This doesn't mean that if we don't specify a type the declaration
remains untyped, just that we let the compiler guess it.

# Functions

##

Functions being first class citizens means that they can be used as
any other value in your program.  They have types, they can be
returned and they can be taken as parameters.

##

Functions are values!

```tut
val intToString: Int => String = { a =>
  a.toString
}
```

##

Functions can be taken as parameters!

```tut
def applyFunction(number: Int, fn: Int => Int): Int =
  fn(number)
  
applyFunction(3, {x => x*3})
applyFunction(2, {x => x+2})
```

##

Functions can be returned!


```tut
def genMultiplication(times: Int): Int => Int = { x =>
  x * times
}

val times3: Int => Int = genMultiplication(3)
times3(3)
```

# Generics

##

Generics are a vital part of abstraction in functional programming. It
allows us to parametrize functions, classes, traits to make them work
for arbitrary types. Let's see an example:

##

```tut
def compose(f: String => Int, g: Int => Boolean): String => Boolean = { str =>
  g(f(str))
}
```

##

```tut
val length: String => Int = _.length
val isEven: Int => Boolean = { i => i % 2 == 0 }

val lengthIsEven = compose(length, isEven)

lengthIsEven("1")
lengthIsEven("10")
lengthIsEven("100")
lengthIsEven("1000")
```

##

But, in the previous example, do we really care about the specific
types of the functions `f` & `g`? or we just need them to match?

##

We could use a **generic** implementation for `compose`!

```tut
def composeGeneric[A, B, C](f: A => B, g: B => C): A => C = { a =>
  g(f(a))
}
``` 

##

And then we could use `composeGeneric` the same way we used `compose`.

##

```tut
val length: String => Int = _.length
val isEven: Int => Boolean = { i => i % 2 == 0 }

val lengthIsEven = composeGeneric(length, isEven)

lengthIsEven("1")
lengthIsEven("10")
lengthIsEven("100")
lengthIsEven("1000")
```

# Algebraic data types

##

Algebraic data types are composite types, made up from smaller ones.
There are two basic constructs for them:

# Case classes

##

case classes (also called **product types**) encode a grouping of other
fields that should **all** be present in all values.

##

```tut:silent
case class Package(length: Int, width: Int, height: Int, weight: Int)
```

In this example we know that all packages should have a length, a
width, a height, and a weight.

##

Case classes have other cool feature, copying.  Copying allows us to
create copies of the object with some fields changed.  This helps
making programs inmutable!

##

```tut:silent
// This is a package of 10x15x20cm and 3 kilograms
val pack = Package(10, 15, 20, 3)
```
##

If we want to change one of the fields of a case class, we just need
to call copy and reassign a new value for the field:

```tut:silent
val pack2 = pack.copy(weight = 2)
```

# Sealed traits

##

Sealed traits (also called **sum types**) encode a type that can be **one
of** all the different invariants:

##

```tut:silent
sealed trait ResponseFromServer
case class OkResponse(json: String) extends ResponseFromServer
case object FailureResponse extends ResponseFromServer
```

Here, we know that a value of the type `ResponseFromServer` can be
either a `OkResponse` or a `FailureResponse`.

# Recap.


| Scala        | Other languages                       |
|--------------|---------------------------------------|
| `trait`      | `interface`(Java), `protocol` (Swift) |
| `class`      | `class`                               |
| `case class` | `POJO/JavaBean` (Java)                |
| `object`     | class containing only static stuff    |
| `val`        | immutable reference (`const` in JS)   |
| `var`        | mutable reference                     |
| `def`        | function/method                       |

# Exercise 1.1

##

Let's imagine a simple event sourced application.  We want to define
some events that we can handle:

##

- An user logs in
- A customer adds an item to the basket
- A user starts payment process
- Payment goes through correctly
- Payment process fails with timeout
- Payment process fails because of Insufficent funds

# solution

##

```tut
import java.util.UUID

sealed trait Event
case class UserLogIn(userId: UUID) extends Event
case class AddItemToBasket(userId: UUID, itemId: UUID) extends Event
case class UserIntentPay(userId: UUID) extends Event
case class PaymentCorrect(userId: UUID, paymentReceipt: String) extends Event

sealed trait PaymentFailure extends Event
case class TimeoutFailure(userId: UUID, intentId: UUID) extends PaymentFailure
case class InsufficentFundsFailure(userId: UUID, inteintId: UUID) extends PaymentFailure
```

# Exercise 1.2

##

We know that all events for this system will have several fields:
- Event ID
- User ID


Refactor your previous exercise to add those.

# solution

```tut
sealed trait Event {
  def id: UUID
  def userId: UUID
}

case class UserLogIn(id: UUID, userId: UUID) extends Event
case class AddItemToBasket(id: UUID, userId: UUID, itemId: UUID) extends Event
case class UserIntentPay(id: UUID, userId: UUID) extends Event
case class PaymentCorrect(id: UUID, userId: UUID, paymentReceipt: String) extends Event

sealed trait PaymentFailure extends Event
case class TimeoutFailure(id: UUID, userId: UUID, intentId: UUID) extends PaymentFailure
case class InsufficentFundsFailure(id: UUID, userId: UUID, inteintId: UUID) extends PaymentFailure

```
