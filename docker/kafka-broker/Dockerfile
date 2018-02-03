FROM rs/kafka


RUN \
    mkdir -p /data/config && \
    mkdir -p /data/scripts && \
    mkdir -p /data/logs

COPY run_kafka.sh /data/scripts/run_kafka.sh
COPY server.properties /data/config/server.properties

RUN \
    chmod +x /data/scripts/run_kafka.sh

EXPOSE 9092

CMD ["/bin/sh", "/data/scripts/run_kafka.sh"]
