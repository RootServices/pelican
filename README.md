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

### Change publish and subscribe settings
In order to change the subscribe and publish settings implement a local `PelicanAppConfig` and override the methods, 
`propertiesForSubscribe`, `propertiesForPublish`. Then use the local implementation to construct publishers and subscribers. 

```java
public class PubSubConfig extends PelicanAppConfig {

    @Override
    public Properties propertiesForSubscribe(String clientId, String consumerGroup) {
        Properties properties = super.propertiesForSubscribe(clientId, consumerGroup);
        properties.put("my.new.prop.key", "my.new.prop.value");
        return properties;
    }

    @Override
    public Properties propertiesForPublish(String clientId) {
        Properties properties = super.propertiesForPublish(clientId);
        properties.put("my.new.prop.key", "my.new.prop.value");
        return properties;
    }
}
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

Docker
----------------

This repo also has the definitions for:
 - [tokensmith/kafka](https://hub.docker.com/repository/docker/tokensmith/kafka)
 - [tokensmith/kafka-broker](https://hub.docker.com/repository/docker/tokensmith/kafka-broker)
 - [tokensmith/zookeeper](https://hub.docker.com/repository/docker/tokensmith/zookeeper)

They contain image tags that mirror this project's versions.
 
The images are to be used for local development only and not production ready.
