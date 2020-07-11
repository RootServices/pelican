# pelican
An interface that can implement publishing and consuming messages from various message queue platforms.


Executing the tests
-------------------

The tests are integration tests and depend on zookeeper and kafka running.

### run zookeeper and kafka ###
```bash
$ make run
```

### stop zookeeper and kafka ###
```bash
$ make stop
```

### execute the tests ###
```bash
./gradlew clean test
```

Configuration
-------------------

### Environment Vars
```bash
export MESSAGE_QUEUE_HOST='localhost:9092'
```

### Injection
```java
    PelicanAppConfig appConfig = new PelicanAppConfig();
    appConfig.setMessageQueueHost("localhost:9092");
```

How to Publish
---------------

```java
PelicanAppConfig appConfig = new PelicanAppConfig();

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