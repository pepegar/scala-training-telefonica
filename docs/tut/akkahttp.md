# Akka HTTP

## plan for today

- What's akka HTTP
- How does it relate to Akka?

# Routing

##

In akka-http routes are described using the routing DSL, let's see a
minimal example:

## akka routing example

`path`, `get`, `post`, `entity(as[T])` directives.

# Directives

Directives are the building blocks for routing.  We've already seen
some directives such as `get`, `path` and `complete`.

# Exercise 10: create a REST API

Migrate the REST api we implemented for play in akka-http.

> Create a CRUD application that stores users. A user is composed by an
> ID and a String.

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

# Testing : Route Testkit

# Exercise 11: Testing our API
