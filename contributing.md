# Contributing


## pelican-build container
*This will be deprecated soon*
```
docker build docker/pelican-build -t pelican-build
docker run -it -p 2181:2181 -p 9092:9092 --hostname localhost -t rs/pelican-build
```

### Run Kafka in pelican-build.
```bash
$ ./scripts/run_zookeeper.sh
$ ./scripts/run_kafka.sh
```

### creat the test topic
```
$ $KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

### Execute tests in pelican-build
```bash
gradle assemble -x signArchives
```

## Zookeeper and Kafka Broker in seperate containers.

### build the containers locally.
```bash
docker build docker/kafka -t rs/kafka
docker build docker/zookeeper -t rs/zookeeper
docker build docker/kafka-broker -t rs/kafka-broker
```

### run zookeeper and broker locally with user-defined network.
```
$ docker network create rs
$ docker run -d -p 2181:2181 -t --name zookeeper --net rs rs/zookeeper
$ docker run -d -p 9092:9092 -t --name broker --net rs rs/kafka-broker
$ docker network connect rs zookeeper
$ docker network connect rs broker

```

### After they are stopped..
```
$ docker ps -aq --filter name=zookeeper
$ docker rm <container_id>
```
