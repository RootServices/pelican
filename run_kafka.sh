#!/bin/bash


nohup bash -c "$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties &"
nohup bash -c "$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties &"
sleep 10
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test


