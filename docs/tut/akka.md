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
basic primitive of computation.  Actors is the smallest unit that
embodies:

<p class="fragment fade-in">processing</p>
<p class="fragment fade-in">storage</p>
<p class="fragment fade-in">communication</p>

##

Actors can do the following:

<p class="fragment fade-in">receive messages</p>
<p class="fragment fade-in">send messages to other actors</p>
<p class="fragment fade-in">create other actors</p>
<p class="fragment fade-in">keep internal state</p>

##

The way communication between actors happen, under the hood, is that
actor A sends a message to actor B, and B receives it in its mailbox.
Mailbox are FIFO queues from which the actor pulls messages when he's
not doing anything else.

##

**Actors**:

- Have addresses. (think of them like their URI within the actorsystem)
- Have mailboxes, in which they receive messages
- Can hold an internal state
- Receive messages
- Can create other actors

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

# Actor addresses

##

Addresses are used to represent the actor in the current
`ActorSystem`.  They're strings, and have the following shape:

```
akka://exercise5/user/ping
akka://exercise5/user/pong
akka://exercise5/user/invoicing/accounts/12341532/calculator
```

##

By default, when we instantiated actors with `actorOf`, they
get a random address chosen by the actor system, but we can customize
the address by passing a second parameter to `actorOf`.


## communicating actors

As we saw in the beginning, one of the properties of actors is that
they can communicate (send & answer) messages to another actors, but
how do we make those actors know each other?

##

There are basically three ways of doing so.  The first one would be to
make the actor take the reference of the other one in its constructor.

```scala
class Actor1(otherActor: ActorRef) extends Actor {
}
```

This works for _static_ references.

##

The second option is to pass the reference as a message:

```scala
val ref1 = system.actorOf(Actor1.props, "actor1")
val ref2 = system.actorOf(Actor2.props, "actor2")

ref1 ! ref2 // here we're sending the ActorRef of Actor2 to Actor1
```

##

The last one is to query the `ActorSystem` for an address by using the
`context.actorSelection` method inside the other actor.  They way one
could do it is:

```scala
system.actorSelection("../actor1") ! msg
```

# exercise 5

- Modify pong actor so it can handle both ping and pong messages.
- Create two instances of it.
- make them start pingponging.

# Actor hierarchy

##

<img src="img/guardians.png" style="height: 400px"/>

##

As we've seen before, actors come in systems and these systems have a
hierarchy.  The actors we create will be all under the `/user` actor,
and everytime one actor instantiates an actor, the latter will be
under the former.  This makes the hierarchy be tree shaped.

##

We need to keep in mind hierarchy of actors when doing several things:

- **Using actor references**.  Actor references are prefixed with
  their parent's reference.
- **Supervision**.  Supervision is handled hierarchically

# Supervision

##

Supervision is a vital part of the actor model.  In the actor model,
when a supervisor actor creates a child actor, it should be able to
handle all the outcomes of crashes & errors in the child actor.  This
process is called supervision.

##

When failures happen to a child actor, the supervisor has four
options:

- **Resume** the subordinate, keeping its accumulated internal state
- **Restart** the subordinate, clearing out its accumulated internal state
- **Stop** the subordinate permanently
- **Escalate** the failure, thereby failing itself

##

https://doc.akka.io/docs/akka/current/general/supervision.html#supervision-and-monitoring

# Actor lifecycle

https://doc.akka.io/docs/akka/current/typed/actor-lifecycle.html remember this is akka-typed

# Exercise 3.2

Come up with an exercise that expands on supervision and lifecycle

# Actor routing

#

##

Routing is a useful technique in akka.  What you do is send messages
to a _frontend_ actor (the _router_) that will then send those
messages to be received by its _routees_.

## Example

https://doc.akka.io/docs/akka/2.5/routing.html


# Testing



[alpakka]: https://developer.lightbend.com/docs/alpakka/current/
