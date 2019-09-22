FROM rs/kafka


RUN \
    mkdir -p /data/config && \
    mkdir -p /data/scripts && \
    mkdir -p /data/logs

COPY run_kafka.sh /data/scripts/run_kafka.sh
COPY run_dummy.sh /data/scripts/run_dummy.sh
COPY server.properties /data/config/server.properties

RUN \
    chmod +x /data/scripts/run_kafka.sh  && \
    chmod +x /data/scripts/run_dummy.sh


EXPOSE 29092
EXPOSE 9092

CMD ["/bin/sh", "/data/scripts/run_dummy.sh"]
