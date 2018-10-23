# Scala & play framework course

This is a course crafted for OO developers that want to learn Scala &
functional programming.

<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [Scala & play framework course](#scala--play-framework-course)
    - [parts of the repository](#parts-of-the-repository)
    - [Sessions](#sessions)
        - [scala](#scala)
        - [Play Framework](#play-framework)
        - [Akka](#akka)
        - [Akka http](#akka-http)
        - [Akka streams](#akka-streams)
        - [Kafka](#kafka)
        - [compiling](#compiling)
    - [building](#building)

<!-- markdown-toc end -->


## parts of the repository

The repo is divided between docs, examples, and exercises.  Docs are
markdown files compiled to revealJS presentations using pandoc.
They're also checked at compile-time to ensure all teh code examples
compile correctly

## Sessions

### scala

Fast introduction to the Scala programming language, trying to show
all differences between functional programming and OOP.

This lives in `docs/tut/scala.md`

### Play Framework

In this session we will see some of the features of Play Framework.
Play is a framework for rapid web development built on top of Scala,
Akka, Akka Streams and Akka HTTP. It showcases resiliency, scalability
and type safety.

This lives in `docs/tut/play.md`

### Akka

In this session we will understand the Actor paradigm and how akka
works in particular.  Akka is a concurrency framework for the JVM,
showcasing the actor paradigm, a stream processing framework, an HTTP
layer, connection to a lot of data systems, etc.

This first session shows the features of the akka-actor package.

This lives in `docs/tut/akka.md`

### Akka http

akka-http is the HTTP layer for Akka.

This lives in `docs/tut/akkahttp.md`

### Akka streams

akka-streams is a reactive streams implementation in Akka.  It
overlaps some features of akka-actor, since both can be used for data
processing, concurrency, etc.  Currently akka-stream is getting a lot
of attention because its ease to use and the type safety it provides
(opposed to akka actors).

This lives in `docs/tut/akkstreams.md`

### Kafka

Kafka is a distributed log system and is the current de facto standard
for system communications in big data & microservices architecture.
It provides a lot of different ways to access the data inside it, from
simple producer-consumer architecture, to streaming, to KSQL.

This lives in `docs/tut/kafka.md`

## building the slides

To build the slides for the course you'll need `pandoc`. Get it from
your package manager.

Once you have it:

```sh
$ make all
```

This will generate HTMLs for the presentation in the `docs/tut-out`
presentation.


## running the examples and exercises

To run an example or exercise, you can just use `sbt`:

``` sh
$ sbt simple/run
```

### tags
| exercise     | description              | solution              |
|:------------:|:------------------------:|:---------------------:|
| exercise 1.1 | exercise-1.1-description | exercise-1.1-solution |
| exercise 1.2 | exercise-1.2-description | exercise-1.2-solution |
| exercise 2.1 | exercise-2.1-description | exercise-2.1-solution |
| exercise 2.2 | exercise-2.2-description | exercise-2.2-solution |
| exercise 3   | exercise-3-description   | exercise-3-solution   |
| exercise 3.2 | exercise-3.2-description | exercise-3.2-solution |
| exercise 4   | exercise-4-description   | exercise-4-solution   |
| exercise 5   | exercise-5-description   | exercise-5-solution   |

