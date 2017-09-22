# pelican
A interface that can implement publishing and consuming messages from various message queue platforms.

https://github.com/junit-team/junit5-samples/blob/master/junit5-vanilla-gradle/build.gradle

Environment Vars
-----------------
```bash
export MESSAGE_QUEUE_HOST='localhost:9092
```

Executing the tests
-------------------
The tests are integration tests and depend that the various message queues are running.



Setting up [Kafka](https://kafka.apache.org/)
------------------
Shamelessy stolen from [kafka's quickstart quide](https://kafka.apache.org/quickstart#quickstart_download).

first,[download kafka](https://kafka.apache.org/downloads).

un-tar it
```bash
$ tar -xzf kafka_2.11-0.11.0.1.tgz
$ cd kafka_2.11-0.11.0.1
```

start zookeeper.
```bash
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```

start kafka server
```bash
$ bin/kafka-server-start.sh config/server.properties
```

create the topic
```bash
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

verify topic was created
```bash
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
```

start a consumer
```bash
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
```

send messages
```bash
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
```