FROM rs/kafka

COPY run_kafka.sh /data/run_kafka.sh

RUN \
    mkdir /data/config

COPY server.properties /data/config/server.properties

RUN \
    chmod +x /data/run_kafka.sh

EXPOSE 9092

CMD ["sh"]
