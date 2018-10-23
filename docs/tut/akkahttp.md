# Akka HTTP

## plan for today

- What's akka HTTP
- How does it relate to Akka?

# Routing

##

In akka-http routes are described using the routing DSL, let's see a
minimal example:

+# Example

##

see `akkaHttpRouting` example.

`path`, `get`, `post`, `entity(as[T])` directives.

# Pathmatchers

path matchers are a vital part of akka http.  They help extract info
from the path.  They're used, for example, to extract segments from
the path.

## Pathmatchers

```scala
"root" / "users" / IntNumber
```

This path matcher will match all the following patterns:

```
root/users/1
root/users/234234234
root/users/97832948729384792
```

## Combinators

- **`/`**: joins two pathmatchers together to produce a new
  pathmatcher with both separated with a slash.

- **`~`**: joins two pathmatchers together to produce a new
  pathmatcher without any separation

- **`|`**: joins two pathmatchers together to produce a new
  pathmatcher that tries the left one first and if it fails, tries the
  right one.

## Extractors

Extractors are parts of the path that can extract info from it.  For
example, `IntNumber` will extract an integer from the path, and fail
otherwise.

## Extractors

We can make extractors optional as follows:

```scala
"root" / IntNumber.?
```

That matcher will match all the following:

```
root/234
root/0
root/
```

## Extractors

Making extractors optional is useful for, for example, handle trailing
slashes in the URL:

```
"hello" / "dolly" ~ Slash.?
```

That extractor can match:

```
hello/dolly/
hello/dolly
```

# Directives

Directives are the building blocks for routing.  We've already seen
some directives such as `get`, `path` and `complete`.

# Marshalling

##

Marshalling is the process of converting an object at one level of
abstraction to a lower level.  For example, the process of converting
a case class to a request body in HTTP.

```
type ToEntityMarshaller[T] = Marshaller[T, MessageEntity]
type ToByteStringMarshaller[T] = Marshaller[T, ByteString]
type ToHeadersAndEntityMarshaller[T] = Marshaller[T, (immutable.Seq[HttpHeader], MessageEntity)]
type ToResponseMarshaller[T] = Marshaller[T, HttpResponse]
type ToRequestMarshaller[T] = Marshaller[T, HttpRequest]
```

##

Te reason we can

# Using Json

##

The easiest way of using JSON in akka-http is creating JsonFormat for
your classes.  What JsonFormat does is, like all other typeclass based
Json libraries out there, create an encoder and decoder from your case
class to json.

##

You can create JsonFormats as follows:

```
case class Car(brand: String, model:String)

implicit val carFormat = jsonFormat2(Car)
```

##

In the previous example, akka knows how to create a jsonFormat for the
case class because of its arity.  You'll need to use `jsonFormatN`
combinator where `N` is the arity of your typeclass.

# Example

See `akkaHttpJson` example

# Exercise 6: create a REST API

`git checkout exercise-6-desciption`

Migrate the REST api we implemented for play in akka-http.

> Create a CRUD application that stores users. A user is composed by an
> ID and a String.

# Exercise 7: integrating with actors

`git checkout exercise-7-desciption`

create a HTTP api for a calculator actor.

The calculator actor should be able to : Sum, Multiply & Divide two given numbers

# Testing

##

As other akka modules, akka-http offers testing capabilities in the
`akka-testkit` module.  You can easily install it by:

```
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test"
```

The route test kit can be used to test routes and check that they
answer what they should.

##

Imagine the following route

```scala
val route = path("/") {
  get {
    complete("Hello world")
  }
}
```

##

Can be tested with:

```scala
Get() ~> route ~> check {
  responseAs[String] shouldEqual "Hello world"
}
```

## Example

See `akkaHttpTestkit` example

# Exercise 8: Testing our API

Create tests for our users api.
