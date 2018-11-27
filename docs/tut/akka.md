# Akka


#

## Plan for today

<p class="fragment fade-in">What's Akka</p>
<p class="fragment fade-in">Akka ecosystem</p>
<p class="fragment fade-in">Actor model</p>
<p class="fragment fade-in">Hierarchy</p>
<p class="fragment fade-in">Supervision</p>
<p class="fragment fade-in">Lifecycle</p>
<p class="fragment fade-in">Routing</p>

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

<p class="fragment fade-in">**processing**</p>
<p class="fragment fade-in">**storage**</p>
<p class="fragment fade-in">**communication**</p>

##

Actors can do the following:

<p class="fragment fade-in">**receive messages**</p>
<p class="fragment fade-in">**send messages to other actors**</p>
<p class="fragment fade-in">**create other actors**</p>
<p class="fragment fade-in">**keep internal state**</p>

##

Actors receive messages in their mailbox.

Mailboxes are FIFO queues from which the actor pulls messages.

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

```scala
sytem.actorOf(MyActor.props, "path")
```


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


# Akka patterns

#

## Ask pattern

The ask pattern allows you to wait for the actor's response to a
message sent to them.  In order to use the ask pattern we need to
import

```scala
import akka.pattern.ask
```

##

The way we use the ask pattern is by using `?` instead of `!`.

```scala
val response = actorRef ? "message"
```

# exercise 6

Create a counter actor that accepts two different messages:

- Increment. Increases an internal counter in 1.
- GetCounter. Sends the current counter back to the sender.

`git checkout exercise-6-description`

#

##

## Pipe pattern

The pipe pattern is used to send the result of a future to an actor
once the future is resolved.

## What is useful for?

```scala
db.queryLastTransactions() pipeTo transactionChecker
```

# exercise 6.1

In the exercise6 project create a new actor called Printer.  This new
actor should handle a `PrintInt()` message that contains an `Int` and
on receival, should print it to the console.

Then, in the main method, pipe the result from the counter actor to
the printer.

`git checkout exercise-6.1-description`


# exercise 7

Let's model a chat application in Akka

`git checkout exercise-7-description`

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

#

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

## Example

see `akkaSupervisionExample`

# Actor routing

#

##

Routing is a useful technique in akka.  What you do is send messages
to a _frontend_ actor (the _router_) that will then send those
messages to be received by its _routees_.

##

This is useful when we want to have one only actor to communicate to
but this actor, to alleviate its load, can create a number of other
actors.

##

Some examples of routing logics one can use are:

- `RoundRobinRoutingLogic`
- `RandomRoutingLogic`
- `SmallestMailboxRoutingLogic`
- `BroadcastRoutingLogic`
- `ScatterGatherFirstCompletedRoutingLogic`
- ...

## Example

see example `actorRouting`

# Testing

#

##

Testing actors can be done using the `akka-testkit` artifact.  Akka
testkit provides several features such as:

- test probes: they're actors in which you can check their messages
  and internal state
- implicit sender: helps us send messages to our actors from outside
  an actor.
  
##

To create a test class, we extend the `TestKit` class as follows:

```scala
class MySpec extends TestKit(ActorSytem("my-test")) with WordSpecLike with Matchers with BeforeAndAfterAll {

}
```

##

In this example we're extending the testkit and also adding a couple
of mixins:

- `WordSpecLike` marks our class as a test to scalatest
- `Matchers` allows us to do assertions in a more idiomatic way
- `BeforeAndAfterAll` allows us to do things on startup of the test and
  on teardown
  
##

The first thing we should do in our akka tests is ensure that the
ActorSystem is terminated at the end of the test.  Otherwise, the test
will hang:

```scala
override def afterAll = {
  TestKit.shutdownActorSystem(system)
}
```

##

After that, we can start by creating the test:

```scala
"MyActor" should {
  "handle Hello messages correctly" in {
  }
}
```

##

Finally, we can start testing inside the inner code block, in this
case, using a `TestProbe()`.  Remember that testprobes allowed us to
send messages to actors and to inspect received messages:

```scala
val sender = TestProbe()
val myActor = system.actorOf(classOf[MyActor])
sender.send(myActor, MyActor.Hello)
val state = sender.expectMsg[World.type]
state shouldBe MyActor.World
```

## Example

See `akkaTesting` example

[alpakka]: https://developer.lightbend.com/docs/alpakka/current/
