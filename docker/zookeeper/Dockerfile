FROM tokensmith/kafka

RUN \
    mkdir -p /data/scripts/ && \
    mkdir -p /data/logs/

COPY run_zookeeper.sh /data/scripts/run_zookeeper.sh
COPY zookeeper.properties /data/config/zookeeper.properties

RUN \
    chmod +x /data/scripts/run_zookeeper.sh

CMD ["/bin/sh", "/data/scripts/run_zookeeper.sh"]
