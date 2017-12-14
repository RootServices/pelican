# Contributing

## Docker
```bash
docker build docker/pelican-build -t pelican-build
docker run -i -t pelican-build
```

# Run Kafka in pelican build.
```bash
nohup $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &
nohup $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties &
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
$KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper localhost:2181
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning > logs/kafka/test_msgs.log &
```

# Execute tests.
```bash
gradle assemble -x signArchives
```

