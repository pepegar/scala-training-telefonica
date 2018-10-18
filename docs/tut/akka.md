# Akka

## Plan for today

- What's Akka
- Akka ecosystem
- Actor model

# What's Akka

##

Akka is an concurrency framework for the JVM.

# Akka ecosystem

## 

Akka started off as a simple actor framework for the JVM but has added
much more stuff ever since.  From streaming capabilities, to HTTP, to
connectors to a lot of data services.

## 

Currently most elements in the ecosystem are focused in akka-streams,
Akka's streaming layer.

# Actor model

#

##

The actor model is a model for concurrency that treats actors as the
basic primitive

##

Actors can do the following:

<p class="fragment fade-in">receive messages</p>
<p class="fragment fade-in">send messages to other actors</p>
<p class="fragment fade-in">create other actors</p>
<p class="fragment fade-in">keep internal state</p>

## How to create actors

```scala
class MyActor extends Actor {
	def receive = {
	  case "hello" => sender() ! "world!"
	}
}
```

## How to create actors

The actor we just implemented receives the "hello" string as a message
and responds with the "world!" string.

<p class="fragment fade-in">
As you see, the way we have to respond to the sender is to use the
sender() method within the actor
</p>

##

But actors can send & receive much more interesting messages, for
example:

```scala
class Calculator extends Actor {
  def receive => {
	case Sum(a, b) => sender() ! Result(a + b)
    case Mult(a, b) => sender() ! Result(a * b)
    case Div(a, b) => sender() ! Result(a / b)
  }
}
```

##

It's a good practice to declare the messages that te actor uses in the
companion object.

```scala
object Calculator {

  //inbound
  case class Sum(a: Int, b:Int)
  case class Mult(a: Int, b:Int)
  case class Div(a: Int, b:Int)
  
  //outbound
  case class Result(i: Int)
}
```

## The actor system

> One actor is no actor, they come in systems

An actor system is a hierarchical group of actors which share common
configuration, e.g. dispatchers, deployments, remote capabilities
and addresses. It is also the entry point for creating or looking up
actors.


##

We can use the `ActorSystem` object to instantiate actors within it:

```scala
val system: ActorSystem = ActorSystem("example")
val calculatorActorRef: ActorRef = system.actorOf(Props(new Calculator))
```

##

What we get when we instantiate a new actor in the `ActorSystem` is a
new `ActorRef`.  We send messages to `ActorRef`s directly.

```scala
calculatorActorRef ! Calculator.Sum(3, 4)
```

##

Another good practice is to move the `props` to the companion object
of the actor as well, as follows:

```scala
object Calculator {
  // ...
  def props: Props = Props(new Calculator)
  // ...
}
```

# exercise 4

Create a pong actor (an actor that, when receives Ping, answers Pong)

#

## communicating actors

As we saw in the beginning, one of the properties of actors is that
they can communicate (send & answer) messages to another actors, but
how do we make those actors know each other?

##

There are basically two ways of doing so.  The first one would be to
make the actor take the reference of the other one in its constructor.

```scala
class Actor1(otherActor: ActorRef) extends Actor {
}
```

This works for _static_ references.

##

The other option is to pass the reference as a message:

```scala
val ref1 = system.actorOf(Actor1.props)
val ref2 = system.actorOf(Actor2.props)

ref1 ! ref2 // here we're sending the ActorRef of Actor2 to Actor1
```

# exercise 5

- Modify pong actor so it can handle both ping and pong messages.
- Create two instances of it.
- make them start pingponging.

# Actor hierarchy

https://doc.akka.io/docs/akka/2.5/general/addressing.html
https://doc.akka.io/docs/akka/2.5/general/remoting.html

# Actor lifecycle

https://doc.akka.io/docs/akka/current/typed/actor-lifecycle.html remember this is akka-typed

# Exercise 3.2

Actor hierarchy

# Actor routing

https://doc.akka.io/docs/akka/2.5/routing.html

# Supervision

https://doc.akka.io/docs/akka/current/general/supervision.html#supervision-and-monitoring

# Testing

[alpakka]: https://developer.lightbend.com/docs/alpakka/current/
