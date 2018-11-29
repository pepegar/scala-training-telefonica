# Akka Streams

#

## Plan for today

- What's akka streams

# Building blocks

##

##

Akka streams has three main building blocks: Sources, Sinks, Flows

## Sources

Sources are emitters of data.  They are parametrized on two types.
The type they produce and the type that they may return when run.

```scala
type Source[Emit, OnRun]
```

## Sinks

Sinks are consumers of data.  They have two generic types as well, the
first being the type of the element they consume, and the second the
type they may return when run.

```scala
type Sink[Consume, OnRun]
```
## Flows

Finally, `Flow` is for intermediate stages of a stream processing app.
They are parametrized on two types, their input and their output.

```
type Flow[Input, Output]
```

## Example

see `akkaStreams` example

#

## Materialization

##

Materialization is the process in which we take a graph and make
something runnable out of it.  Unless we materialize a set of flows,
sinks and sources, we wont be able to run the process.

It happens on terminal operations, such as `run` or
`runWith`.

## Fusion

Materialization applies fusion to our operations.  This means that if
our operations are not async and can be combined together they will be
run by the same actor.  This enhances performance notably, since it
avoids async boundaries as much as possible.

## Exercise 13: Tying sources, sinks, and flows together

`git checkout exercise-13-description`

# Graph DSL

##

##

When we are defining complex topologies we can rely on the graph DSL.

##

There are some cool pieces we can use when constructing a graph using
the DSL such as:

##

<img src="img/fanout.png" style="height: 400px"/>

## Fanouts

broadcasts will send all messages to all its outlets, and balances
will only send it to one of them.

##

<img src="img/fanin.png" style="height: 400px"/>

## Example

See the `akkaGraphDsl` example

# Exercise 14: Describing complex topologies with the graph DSL

`git checkout exercise-14-description`

#

## Error handling in streams

Errors will occur in a stream application and akka-streams gives us
several different ways we can handle them.

## .log

```scala
Source(-5 to 5)
  .map(1 / _) //throwing ArithmeticException: / by zero
  .log("error logging")
  .runWith(Sink.ignore)
```

## recover

```scala
Source(0 to 6).map(n ⇒
  if (n < 5) n.toString
  else throw new RuntimeException("Boom!")
).recover {
  case _: RuntimeException ⇒ "stream truncated"
}.runForeach(println)
```

## Example

see the `akkaStreamsErrorHandling` project

## supervision strategies

As with actors, supervision strategies for streams allow us to:

- **Stop** - The stream is completed with failure.
- **Resume** - The element is dropped and the stream continues.
- **Restart** - The element is dropped and the stream continues after
  restarting the operator. Restarting an operator means that any
  accumulated

##

Supervision strategies for actors can be created in a `Decider`:

```scala
val decider: Supervision.Decider = {
  case _: Exception => Supervision.Resume
  case _            => Supervision.Stop
}
```

# Exercise 15

`git checkout exercise-15-description`

# Advanced combinators

##

## Compression

When dealing with compressed data, we can use the `Compression` object
to decompress it in a stream:

```scala
val uncompressed = compressed.via(Compression.gunzip())
  .map(_.utf8String)
```

## Framing

We can use the `Framing` object to split data given a delimiter within
a flow!

```scala
val linesStream = rawData.via(Framing.delimiter(
  ByteString("\r\n"), maximumFrameLength = 100, allowTruncation = true))
  .map(_.utf8String)
```

## Example

see the `akkaStreamCombinators`

## Exercise 16

use the Framing object to implement the lines delimiter instead of
doing by hand.

`git checkout exercise-16-description`

#

## Buffers

Buffers are a great way of dealing with inconsistencies between the
speed of producers and consumers in different parts of my application.

## 

Imagine we have a fast producer, something like this:

```
Source.repeat(1)
```

##

And, we have a not-so-fast consumer, something like this:

```
Flow[Int].map { i =>
  Thread.sleep(50)
  i * i
}
```

##

The problem we'll face in this case is that the consumer will not be
able to keep up with the speed of the producer and the performance of
the system can be really degraded.

## solution

A solution we can use for this problem is creating a buffer and
placing it between producer and consumer.  Later on, in the buffer
we'll be able to select the overflow strategy:

## Example

see `akkaStreamsBackpressure`


# Integrating Akka streams with our akka actors
# Testing
# Exercise 15: testing stream applications
