# Kafka

#


## Plan for today

<p class="fragment fade-in">course survey</p>
<p class="fragment fade-in">*from last session*: integrate actors in akka-streams</p>
<p class="fragment fade-in">Kafka introduction</p>
<p class="fragment fade-in">Producers/Consumers</p>
<p class="fragment fade-in">`alpakka-kafka`</p>
<p class="fragment fade-in">End of course project</p>

#

## Quick survey

Please follow the link and take the survey, it'll not take more than
10 minutes!

http://bit.ly/akkahackrs

#

## From last session

[integrate akka actors with akka-streams](file:///home/pepe/projects/47deg/telefonica-training/docs/tut-out/akkastreams.html)

#

## What's Kafka

Kafka is a:

<p class="fragment fade-in">Distributed streaming platform</p>
<p class="fragment fade-in">Stream data platform</p>
<p class="fragment fade-in">Distributed commit log</p>
<p class="fragment fade-in">Messaging platform</p>
<p class="fragment fade-in">Database?</p>

## Kafka basics

A whirlwind tour over Kafka main features.

## Records

Records are also called commits, events, messages, etc.

- `{key-value-timestamp}`
- Immutable
- **Persited to disk**

## Brokers

- Brokers are nodes in the cluster
- Producers write records to brokers, consumers pull records from
  brokers
- Leader/Follower approach for cluster distribution (backed by
  Zookeeper).
  
## Topics & partitions

- A topic is a logical name for one or more partitions.  Think of
  topic as the _commit log_ you want to write to, for example
  _purchases_, _audit-log_,...
- Partitions are replicated
- Message ordering is only guaranteed at the partition level

## Topics & partitions

<img src="img/topics.jpg" style="height: 400px"/>

## Offsets

- Offset is the unique sequential id per partition (think
  auto_increment long id *per partition*).
- Consumers track offsets
- Using offsets have some benefits: time traveling, different speed
  for different consumers...
  
## Producer partitioning

- When we send a message from the producer, we do it to a given
  partition and topic.  Writes go tot the leader of the partition.
- Partitioning can be done manually or based on the key of the
  message.
- Replication factor is topic based

## Consumer groups

- They help scaling-out consumption in Kafka.
- Message consumption is load-balanced between all consumers in the
  consumer-group

## Delivery guarantees (Producer side)

- **Async**.  No delivery guarantees. Best performance (_fire and
  forget_).
- **Committed to Leader**.  We can choose guarantees of the message
  being committed to the leader.
- **Comitted & cluster quorum**. Guarantees leader commit and cluster
  quorum.

## Delivery guarantees (Consumer side)

- **At least once (default)**. Commit to kafka happens all messages in
  the block are read.
- **At most once**. Commits when the consumer receives the
  block. Won't re-deliver.
- **Effectively once**. Similar to ALO, but tweaked a bit so we won't
  process msgs several times.
- **Exactly once**.

# Install confluent platform

##

Goto https://www.confluent.io/download/

#

## Producers/consumers

Kafka has an official java SDK that is possible to use to
produce/consume data.  We will not get very deep into it though, since
we want to focus on the alpakka-kafka library more.

##

see `kafkaExample`

#

## alpakka

Alpakka is a set of integrations for akka-streams.  If the service is
somewhat famous, chances are that it's already in Alpakka.

- S3
- Dynamo
- FTP
- Elasticsearch
- **Kafka**
...

## alpakka-kafka

alpakka-kafka is the kafka integration for alpakka.  It maps kafka
concepts to akka-streams:

- Producer -> Source
- Stream -> Flow
- Consumer -> Sink

It overlaps in functionality with kafka streams, it doesn't make much
sense to use both together.

## Example

see the `alpakkaExample`

## Exercise

`git checkout exercise-18-description`

## Exercise

`git checkout exercise-19-description`

#

## End of course project :)

Let's tie everything we've learned together and make an event sourced
scala app backed by akka-streams and Kafka!

**Idea**, bank:
- Bank account [Create|Close]
- Deposit
- Withdrawal
