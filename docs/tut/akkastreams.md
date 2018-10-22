# Akka Streams

## Plan for today

- What's akka streams

# Building blocks

##

Akka streams has three main building blocks: Sources, Sinks, Flows

## Sources

Sources are emitters of data.  They are parametrized on two types.
The type they produce and the type that they may emit when run.

```scala
type Source[Emit, OnRun]
```

## Sinks

On the other hand, `Sinks` are consumers of data.  They hace

# Materialization

Materialization is the process in which we take a graph and make
something runnable out of it.  Unless we materialize a set of flows,
sinks and sources, we wont be able to run the process.

# Exercise 12: Tying sources, sinks, and flows together
# Graph DSL
# Exercise 13: Describing complex topologies with the graph DSL
# Error handling in streams
# Advanced combinators
# Parallelism
# Exercise 14: putting parallelism in practice
# Integrating Akka streams with Play
# Buffers, Back Pressure
# Testing
# Exercise 15: testing stream applications
