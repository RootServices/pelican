#!/bin/sh

$KAFKA_HOME/bin/zookeeper-server-start.sh /data/config/zookeeper.properties &> /data/logs/zookeeper.log
