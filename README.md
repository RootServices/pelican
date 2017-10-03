# pelican
A interface that can implement publishing and consuming messages from various message queue platforms.

Environment Vars
-----------------
```bash
export MESSAGE_QUEUE_HOST='localhost:9092'
```

Executing the tests
-------------------
The tests are integration tests and depend that the various message queues are running. See, "Setting up Kafka" section below.

```bash
gradle test
```

How to Publish
---------------

```java
AppConfig appConfig = new AppConfig();

Map<String, String> message = new HashMap<>();
message.put("test_key", "test_value");

Publish publish = appConfig.publish();

String topic = "test";
publish.send(topic, message);
```

How to Subscribe
----------------

```java
AppConfig appConfig = new AppConfig();


List<String> topics = Arrays.asList("test");
String consumerGroup = "test";

Subscribe subject = appConfig.subscribe(topics, consumerGroup);

Long timeout = 100;
List<Map<String, String>> messages = subject.poll(timeout);

// tell the queue the record has been processed.
subject.processed();
```

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

Upload the JAR
--------------
```bash
$ gradle clean signArchives uploadArchives
```