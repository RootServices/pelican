# pelican
A interface that can implement publishing and consuming messages from various message queue platforms.

Environment Vars
-----------------
```bash
export MESSAGE_QUEUE_HOST='localhost:9092'
```

Executing the tests
-------------------

The tests are integration tests and depend on zookeeper and kafka running.

### start zookeeper and kafka ###
```bash
$ make build-docker
$ make start
```

### execute the tests ###
```bash
./gradlew clean test
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

Duration timeout = Duration.ofSeconds(100);
List<Map<String, String>> messages = subject.poll(timeout);

// tell the queue the record has been processed.
subject.processed();
```